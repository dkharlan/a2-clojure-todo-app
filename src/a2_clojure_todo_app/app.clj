(ns a2-clojure-todo-app.app
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :refer [redirect]]
            [ring.adapter.jetty :refer [run-jetty]]
            [a2-clojure-todo-app.templates :as templates]
            [a2-clojure-todo-app.state :as state]))

(defn make-routes [state!]
  (routes
    (GET "/" []
      (templates/index (get @state! :items)))
    (GET "/item/:item-id/delete" [item-id]
      (swap! state! state/delete-item (Integer/parseInt item-id))
      (redirect "/"))
    (POST "/item/create" [description]
      (swap! state! state/create-item description)
      (redirect "/"))
    (route/not-found (templates/not-found))))

(defn make-app [routes]
  (-> routes
    (wrap-defaults site-defaults)))

(defn run-app!
  ([app port]
   (run-app! app port false))
  ([app port join?]
   (run-jetty app {:port port :join? join?})))

