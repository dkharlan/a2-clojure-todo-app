(ns a2-clojure-todo-app.core
  (:gen-class)
  (:require [a2-clojure-todo-app.app :as app]
            [a2-clojure-todo-app.state :as state]))

(defn start-app!
  ([state! port]
   (start-app! state! port false))
  ([state! port join?]
   (-> state!
     (app/make-routes)
     (app/make-app)
     (app/run-app! port join?))))

(defn -main
  [& args]
  (let [state! (atom state/initial-state)]
    (start-app! state! 8080 true))) ;; in the REPL omit the last parameter (or pass false)
                                    ;; to prevent the REPL from blocking

