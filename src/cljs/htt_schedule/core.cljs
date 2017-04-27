(ns htt-schedule.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [htt-schedule.sessions :as sessions]))

(enable-console-print!)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Data structure for our application state

(defonce app-state (atom {:text "ProgsCon Awesomenessness"
                          :schedule []}))


;; A very naughty hard coded twitter profile (should get from picture via twitter api)
;; Until then we are playing "Being John Stevenson"
(def hard-coded-tiwtter-profile
  "https://pbs.twimg.com/profile_images/857274701391028224/Ocn-IuEC_400x400.jpg")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Om Components

;; Components for session detail

(defn twitter-handle [cursor component]
  (reify
    om/IRender
    (render [this]
      (dom/a
       #js {:href (str "https://twitter.com/" cursor)}
       (str "@" cursor)))))

(defn twitter-profile-picture [cursor component]
  (reify
    om/IRender
    (render [this]
      (dom/div nil
        (dom/img
         #js {:src hard-coded-tiwtter-profile
              :width  "50px"
              :height "50px"})))))

(defn session-star [cursor component]
  (reify
    om/IRender
    (render [this]
      (dom/button
       #js {:className "btn btn-default btn-sm glyphicon glyphicon-star"
            :onClick #(println "update the star icon vote")}
       (str "Vote for " cursor)))))

;; TODO: use core.async to clear all the stars ?


(defn session-detail [cursor component]
  (reify
    om/IRender
    (render [this
]
      (dom/div
       #js {:className "panel panel-primary"}

       (dom/div
        #js {:className "panel-heading"} (:title cursor))

       (dom/div
        #js {:className "panel-body"}
        (dom/div nil (:description cursor))

        (om/build twitter-handle (:twitter-handle cursor))
        (om/build twitter-profile-picture (:twitter-handle cursor))
        (om/build session-star (:twitter-handle cursor)))))))


(defn schedule-builder [cursor component]
  (reify
    om/IRender
    (render [this]
      (dom/div nil
        (for [session-data (:schedule cursor)]
          #_(dom/p nil "Please, Lets go loopy")
          (om/build session-detail session-data))))))


(defn session-add [cursor component]
  (reify
    om/IInitState                       ; component-local state (rather than just input fields)
    (init-state [this]
      {:title ""
       :description ""
       :twitter-handle ""})
    om/IRenderState
    (render-state [this state]          ; component will re-render when state changes
      (dom/div nil
        (dom/h3 nil "Add New Session")

        (dom/form
         #js {:className "form-horizontal"}

         (dom/input
          #js {:type "text"
               :className "form-control"
               :value (:title state)
               :onChange
                 (fn [event]
                   (om/set-state! component :title
                     (-> event .-target .-value)))
               :placeholder "Session Title"})

         (dom/textarea
          #js {:className "form-control"
               :placeholder "Session description"
               :value (:description state)
               :onChange
                 (fn [event]
                   (om/set-state! component :description
                     (-> event .-target .-value)))
               :rows "5"})

         (dom/input
          #js {:type "text"
               :className "form-control"
               :value (:twitter-handle state)
               :onChange
                 (fn [event]
                   (om/set-state! component :twitter-handle
                     (-> event .-target .-value)))
               :placeholder "Twitter handle"})

         (dom/button
          #js {:className "btn btn-primary"
               :onClick
                 (fn [e]
                   (.preventDefault e)
                   (om/transact! cursor :schedule
                     (fn [new-talk]
                       ((fnil conj []) new-talk
                        {:title (:title state)
                         :description (:description state)
                         :twitter-handle (:twitter-handle state)}))))}
          "Add New Session"))))))


(defn root-component [app owner]
  (reify
    om/IRender
    (render [_]
      (dom/div
        #js {:className "container"}  ;; creates javascript {className : "container"}
        (dom/p #js {:className "jumbotron" :style #js {:fontSize "42px"} } (:text app))
        (om/build schedule-builder app)
        (om/build session-add app)))))


(om/root
 root-component
 app-state
 {:target (js/document.getElementById "app")})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; REPL Driven Development code

#_(swap! app-state update :text #(str "Hello from the repl"))

#_(swap! app-state assoc :schedule [])

#_(swap! app-state update :schedule conj {:title "Opening Keynote"})

@app-state


;; Example talk schedule data

;; Fake ProgsCon sessions
;; commented out with #_ reader macro,
;; so are only called when we evaluate them in the repl

#_(swap! app-state
       update :schedule
       conj {:title "Opening Keynote"
             :description "Words can be very inspirational"
             :twitter-handle "jr0cket" })

#_(swap! app-state
       update :schedule
       conj {:title "A Glass of CIDER"
             :description "Unleashing the power of Clojure with Emacs"
             :twitter-handle "batisov" })

#_(reset! app-state {:text "ProgSCon Awesomenessness"
                     :schedule []})


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Helper functions to load sessions quickly or reset them

;; Adding the talks to the app-state so they render on the page
(defn sessions-progcon []
  (do
    (swap! app-state update :schedule conj sessions/trisha-gee)
    (swap! app-state update :schedule conj sessions/michael-nitschinger)))


;; Reset all sessions, just include the title

(defn sessions-reset []
  (reset! app-state {:text "ProgsCon Awesomenessness"
                     :schedule []}))

;; Reset and reload sessions

(defn session-reload []
  (do
    (sessions-reset)
    (sessions-progcon)))
