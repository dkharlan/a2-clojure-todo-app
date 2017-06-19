(ns a2-clojure-todo-app.templates
  (:require [hiccup.core :refer [html]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]))

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

