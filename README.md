# a2-clojure-todo-app

This is a simple to-do list to demonstrate web development concepts for a A2 Clojure
meetup. Check out the commit history for a blow-by-blow explanation, and see [here for the slides](https://goo.gl/egsZjR).

## Usage

The `master` branch is the finished sample; checkout the `start-here` branch if you'd like to code this yourself.

Open a REPL and run the following to start the app:

```clojure
(require '[a2-clojure-todo-app.core :refer [start-app!]])
(require '[a2-clojure-todo-app.state :refer [initial-state]])
(def state! (atom initial-state))
(def server (start-app! state! 8080))
```

Then you can make whatever changes you like.

To stop and start the the app in the REPL (e.g. if you change routes), do:

```clojure
(.stop server)
(def server (start-app! state! 8080))
```

You can also build an uberjar and run from the command line:

    $ lein uberjar
    $ java -jar a2-clojure-todo-app-0.1.0-standalone.jar [args]

## License

Copyright © 2017 David Harlan

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

## Other Content

The [Clojure logo](https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Clojure_logo.svg/400px-Clojure_logo.svg.png) on the index page is from Wikipedia.
