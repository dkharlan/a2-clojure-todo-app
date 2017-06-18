(ns a2-clojure-todo-app.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :refer [html]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.adapter.jetty :refer [run-jetty]]))

(defn page [body]
  [:html
   [:head
    [:title "To Do"]]
   [:body body]])

(defn render-page [body]
  (-> body
    (page)
    (html)))

(defn todo-list [items]
  [:div
   [:ul
    (for [item items]
      [:li item])]])

(defn index [items]
  (render-page
    [:div {:id ""}
     (if (> (count items) 0)
       (todo-list items)
       [:p "You have nothing to do."])]))

(defn not-found []
  (render-page
    [:p "Sorry, the page you're looking for does not exist."]))

(defn make-routes [state!]
  (routes
    (GET "/" [] (index (get @state! :items)))
    (route/not-found (not-found))))

(defn make-app [routes]
  (-> routes
    (wrap-defaults site-defaults)))

(defn run-app!
  ([app port]
   (run-app! app port false))
  ([app port join?]
   (run-jetty app {:port port :join? join?})))

(def initial-state
  {:items []})

(defn -main
  [& args]
  (let [state! (atom initial-state)]
    (-> state!
      (make-routes)
      (make-app)
      (run-app! 8080 true) ;; in the REPL use false to prevent the REPL from blocking
      )))

