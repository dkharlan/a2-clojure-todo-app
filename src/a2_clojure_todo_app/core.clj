(ns a2-clojure-todo-app.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :refer [html]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :refer [redirect]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
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
    (for [[item-id item-description] items]
      [:li
       [:span item-description "&nbsp;"]
       [:a {:href (str "/item/" item-id "/delete")} "Delete"]])]])

(defn new-item-form []
  [:div
   [:form {:action "/item/create" :method "post"}
    (anti-forgery-field)
    [:input {:type "text" :name "description" :required true}]
    [:button "Create"]]])

(defn index [items]
  (render-page
    [:div {:id ""}
     (if (> (count items) 0)
       (todo-list items)
       [:p "You have nothing to do."])
     (new-item-form)]))

(defn not-found []
  (render-page
    [:p "Sorry, the page you're looking for does not exist."]))

(defn make-routes [state!]
  (routes
    (GET "/" []
      (index (get @state! :items)))
    (GET "/item/:item-id/delete" [item-id]
      (do
        (swap! state! update-in [:items] dissoc (Integer/parseInt item-id))
        (redirect "/")))
    (POST "/item/create" [description]
      (swap! state!
        #(let [{:keys [next-id] :as state} %]
           (-> state
             (assoc-in [:items next-id] description)
             (update-in [:next-id] inc))))
      (redirect "/"))
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
  {:items {}
   :next-id 0})

(def test-state
  {:items {0 "Walk the dog"
           1 "Go to work"
           2 "Go shopping"}
   :next-id 2})

(defn -main
  [& args]
  (let [state! (atom initial-state)]
    (-> state!
      (make-routes)
      (make-app)
      (run-app! 8080 true) ;; in the REPL use false to prevent the REPL from blocking
      )))

