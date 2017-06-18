(ns a2-clojure-todo-app.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :refer [html]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.adapter.jetty :refer [run-jetty]]))

(defn index []
  [:html
   [:head
    [:title "To Do"]]
   [:body
    [:p "Hello, world!"]]])

(defn not-found []
  [:html
   [:head
    [:title "Not Found"]]
   [:body
    [:p "Sorry, the page you're looking for does not exist."]]])

(defroutes app-routes
  (GET "/" [] (html (index)))
  (route/not-found (html (not-found))))

(def app (wrap-defaults app-routes site-defaults))

(defn run! [app port]
  (run-jetty app {:port port :join? false}))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

