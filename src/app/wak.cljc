(ns app.wak
  (:require contrib.str
            #?(:clj [datascript.core :as d]) ; database on server
            [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom :refer (div text props a)]
            [hyperfiddle.electric-ui4 :as ui]))

#?(:clj (defonce !conn (d/create-conn {}))) ; database on server
(e/def db) ; injected database ref; Electric defs are always dynamic

(e/defn Main []
  (e/server
    (e/client
      (a
        (props {::dom/href "https://chat.openai.com/g/g-ZtM1zw6Bs-uwaggud-ai"})
        (text "real wakgood is here")))
    #_(binding [db (e/watch !conn)]
        (e/client
          (dom/link (dom/props {:rel :stylesheet :href "/todo-list.css"}))
          (dom/h1 (dom/text "minimal todo list"))
          (dom/p (dom/text "it's multiplayer, try two tabs"))
          (dom/div (dom/props {:class "todo-list"})
                   (TodoCreate.)
                   (dom/div {:class "todo-items"}
                            (e/server
                              (e/for-by :db/id [{:keys [db/id]} (todo-records db)]
                                (TodoItem. id))))
                   (dom/p (dom/props {:class "counter"})
                          (dom/span (dom/props {:class "count"}) (dom/text (e/server (todo-count db))))
                          (dom/text " items left")))))))
