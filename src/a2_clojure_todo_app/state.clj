(ns a2-clojure-todo-app.state)

(def initial-state
  {:items {}
   :next-id 0})

(def test-state
  {:items {0 "Walk the dog"
           1 "Go to work"
           2 "Go shopping"}
   :next-id 2})

(defn delete-item [state item-id]
  (update-in state [:items] dissoc item-id))

(defn create-item [{:keys [next-id] :as state} description]
  (-> state
    (assoc-in [:items next-id] description)
    (update-in [:next-id] inc)))

