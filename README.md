# Hetzner Cloud Java API

![](https://github.com/CubitsDev/hcloud-java/actions/workflows/junit-tests.yml/badge.svg) ![](.github/badges/jacoco.svg)

hcloud-java is a simple and easy to use library to interact with the Hetzner Cloud API. It is fully tested and has few dependencies, as to not add extra bloat.

The library is designed to follow an Observer pattern, meaning the majority of requests to the API will be made in the background, without being explicitly called. For example, updating a server's name with it's .setName() property would automatically call the Hetzner Cloud API and run this update for you.

## Current WIP. No released version

The initial version is unlikely to implement all of the API, due to the extensive work needed. It is likely to release with at **least** the following:

- All Server Functionality (Actions, creation, deletion, etc)
- All Location Functionality
- All Network Functionality

The goal is to have all of the API mapped eventually.

Contributions are welcome! Equally, new requests, bugs or general issues are welcome in the Issues tab.
