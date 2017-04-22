(ns htt-schedule.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(defonce app-state (atom {:text "Hello HackTheTower Chestnut!"
                      :schedule []}))

(defn talk-detail [cursor component]
  (reify
    om/IRender
    (render [this]
      (dom/div #js {:className "panel panel-primary"}
               (dom/div #js {:className "panel-heading"} (:title cursor))
               (dom/div #js {:className "panel-body"}
                        (dom/div nil (:description cursor))
                        (dom/a #js {:href (str "https://twitter.com/" (:twitter-handle cursor))} (str "@" (:twitter-handle cursor))))))))

#_(dom/div nil
           (dom/div #js {:style {:color "blue"}} (:title cursor))
           (dom/div nil (:description cursor))
           (dom/a #js {:href (str "https://twitter.com/" (:twitter-handle cursor))} (:twitter-handle cursor)))

(defn schedule [cursor component]
  (reify
    om/IRender
    (render [this]
      (dom/div nil
             (for [talk (:schedule cursor)]
               #_(dom/p nil "Please, Lets go loopy")
               (om/build talk-detail talk))))))


;; talk
#_{:tile " "
 :description " "
 :twitter-handle ""}

;; schedule
#_[{:tile " "
  :description " "
  :twitter-handle ""}
 {:tile " "
  :description " "
  :twitter-handle ""}]

(defn root-component [app owner]
  (reify
    om/IRender
    (render [_]
      (dom/div nil
               (dom/h1 nil (:text app))
               (om/build schedule app)))))

(om/root
 root-component
 app-state
 {:target (js/document.getElementById "app")})


#_(swap! app-state update :text #(str "Hello from the repl"))

#_(swap! app-state assoc :schedule [])

#_(swap! app-state update :schedule conj {:title "Opening Keynote"})

@app-state

#_(swap! app-state update :schedule conj {:title "Opening Keynote"
                                        :description "Something very inspirational"
                                        :twitter-handle "jr0cket" })

(swap! app-state update :schedule conj {:title "A Glass of CIDER"
                                        :description "Unleashing the power of Clojure with Emacs"
                                        :twitter-handle "batisov" })

#_(reset! app-state {:text "Reseting Title"
                   :schedule []})

















;; (def blah {:text "fofdjf"  })
;; (assoc blah :schedule "number one")
;; (def foobar (assoc blah :schedule "number one"))
;; foobar

;; (def my-map {:a "fish"})
;; (get my-map "a")
;; (my-map :a)
;; (:a my-map)
