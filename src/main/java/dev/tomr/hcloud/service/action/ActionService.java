package dev.tomr.hcloud.service.action;

import dev.tomr.hcloud.HetznerCloud;
import dev.tomr.hcloud.http.HetznerCloudHttpClient;
import dev.tomr.hcloud.http.RequestVerb;
import dev.tomr.hcloud.http.model.Action;
import dev.tomr.hcloud.http.model.ActionWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ActionService {
    protected static final Logger logger = LogManager.getLogger();

    private final HetznerCloudHttpClient client = HetznerCloudHttpClient.getInstance();

    public ActionService() {
    }

    public CompletableFuture<Action> waitForActionToComplete(Action action) {
        return CompletableFuture.supplyAsync(() -> {
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            List<String> hostAndKey = HetznerCloud.getInstance().getHttpDetails();
            List<Future<Action>> futures = new ArrayList<>();
            AtomicReference<Action> completedAction = new AtomicReference<>();
            futures.add(scheduler.schedule(createCheckCallable(action, hostAndKey), 0, TimeUnit.MILLISECONDS));

            try {
                for (int i = 0; i < futures.size(); i++) {
                    Future<Action> future = futures.get(i);
                    if (future.isDone()) {
                        if (future.get() != null) {
                            completedAction.set(future.get());
                        } else {
                            futures.add(scheduler.schedule(createCheckCallable(action, hostAndKey), 1000L * (i + 1), TimeUnit.MILLISECONDS));
                        }
                    } else {
                        i--;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            futures.forEach((f) -> {
                f.cancel(true);
            });
            return completedAction.get();
        });
    }

    private Callable<Action> createCheckCallable(Action action, List<String> hostAndKey) {
        String url = String.format("%sactions/%d", hostAndKey.get(0), action.getId());

        return () -> {
            try {
                Action newAction = client.sendHttpRequest(ActionWrapper.class, url, RequestVerb.GET, hostAndKey.get(1)).getAction();
                if (newAction.getError() != null) {
                    throw new Exception(String.format("Error from Hetzner: %s, %s", newAction.getError().getMessage(), newAction.getError().getCode()));
                }else if (newAction.getProgress() == 100 && newAction.getFinished() != null) {
                    return newAction;
                } else {
                    return null;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
