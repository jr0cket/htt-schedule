(ns htt-schedule.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

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


(defn star-session [cursor component]
  (reify
    om/IRender
    (render [this]
      (dom/button
       #js {:className "btn btn-default btn-sm glyphicon glyphicon-star"
            :onClick #(println "update the star icon vote")}
       (str "Vote for " cursor)))))

;; TODO: use core.async to clear all the stars ?

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


(defn talk-detail [cursor component]
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
        (om/build star-session (:twitter-handle cursor)))))))


;; not rendering bootstrap properly
#_(defn talk-detail-media [cursor component]
  (reify
    om/IRender
    (render [this]

      (dom/div
       #js {:className "media media-right"}

       (dom/div
        #js {:className "media-heading"} (:title cursor))

       (dom/a
        #js {:className "media-body"
             :href (str "https://twitter.com/" cursor "/profile.png")})

        (dom/div nil (:description cursor))

        (om/build twitter-handle (:twitter-handle cursor))))))


(defn schedule [cursor component]
  (reify
    om/IRender
    (render [this]
      (dom/div nil
        (for [talk (:schedule cursor)]
          #_(dom/p nil "Please, Lets go loopy")
          (om/build talk-detail talk))))))


(defn add-session [cursor component]
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
        (om/build schedule app)
        (om/build add-session app)))))


(om/root
 root-component
 app-state
 {:target (js/document.getElementById "app")})


#_(swap! app-state update :text #(str "Hello from the repl"))

#_(swap! app-state assoc :schedule [])

#_(swap! app-state update :schedule conj {:title "Opening Keynote"})

@app-state


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Example talk schedule data

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


#_(def trisha-gee
  {:title "Becoming Fully Buzzword Compliant"
   :bio
     "I’m a developer / technical advocate / educator, based in Spain and working remotely for JetBrains.
      I love the combination of solving technical problems and working out the best way to teach other developers techniques that will make their lives easier.

      I’m a leader of the Sevilla MongoDB and Java User Groups, and a key member of the London Java Community.
      In 2014 I became a Java Champion and I’m a 2015 MongoDB Master."
   :description
     "It’s all about Containers, Serverless and Reactive Programming right now!
      ProgSCon London will explore these trends with leading industry experts.
      Several talks will also feature Blockchain, Microservices and Big Data.

      You’re here at ProgSCon to hear all about the latest trends in technology,
      to learn about them and decide which ones to apply and figure out how.
      But it’s a tall order, learning to be a fully buzzword compliant developer, architect or lead,
      especially when What’s Hot changes on practically a daily basis.

      During this talk, Trisha will give an irreverent overview of the current technical landscape
      and present a survival guide for those who want to stay ahead in this turbulent industry."
   :twitter-handle "trisha_gee"})

#_(def trisha-gee-image "http://2017.progscon.co.uk/wp-content/uploads/2016/11/TrishaGee-150x150.jpg")

#_(swap! app-state update :schedule conj trisha-gee)


;; other speakers to include
;; http://2017.progscon.co.uk/cr3ativspeaker/jan-machacek/
;; http://2017.progscon.co.uk/cr3ativspeaker/ramkumar-aiyengar/
;; http://2017.progscon.co.uk/cr3ativspeaker/steve-poole/


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Reset data

#_(reset! app-state {:text "ProgsCon Awesomenessness"
                   :schedule []})






;; Other ideas

;; Slurp the progscon schedule into the app from a file (or scrapped from the website for complete awesomenessness)



;; Media heading with twitter profile picture

;; Add media heading
;; <div class="media">
;; <div class="media-left">  or <div class="media-left media-middle">
;; <a href="#">
;; <img class="media-object" src="..." alt="...">
;; </a>
;; </div>
;; <div class="media-body">
;; <h4 class="media-heading">Media heading</h4>
;; ...
;; </div>
;; </div>


;; Sign in with Twitter button
;; <a class="btn btn-block btn-social btn-twitter">
;; <span class="fa fa-twitter"></span> Sign in with Twitter
;; </a>

;; Now that we’ve actually installed it, let’s go ahead and start adding the icons. Continuing with our footer, I’m going to go:
;; <div class="navbar-text pull-right"></div>
;; <div class="navbar-text pull-right"></div>
;; Now I will actually add the icons:
;; <a href="#"><i class="fa fa-facebook-square"></i></a>
;; <a href="#"><i class="fa fa-facebook-square"></i></a>
;; So now if we go to our preview, we’ll see the Facebook icon, but you’ll notice that it’s pretty small. So what I’m going to do is increase the size by adding “fa-2x.” Now you’ll see that it’s quite a bit bigger. Now I’m just going to add a few more:
;; <a href="#"><i class="fa fa-twitter fa-2x"></i></a>
;; <a href="#"><i class="fa fa-google-plus fa-2x"></i></a>
;; <a href="#"><i class="fa fa-twitter fa-2x"></i></a>
;; <a href="#"><i class="fa fa-google-plus fa-2x"></i></a>



;; Embed video

;; <!-- 16:9 aspect ratio -->
;; <div class="embed-responsive embed-responsive-16by9">
;; <iframe class="embed-responsive-item" src="..."></iframe>
;; </div>

;; <!-- 4:3 aspect ratio -->
;; <div class="embed-responsive embed-responsive-4by3">
;; <iframe class="embed-responsive-item" src="..."></iframe>
;; </div>







;; (def blah {:text "fofdjf"  })
;; (assoc blah :schedule "number one")
;; (def foobar (assoc blah :schedule "number one"))
;; foobar

;; (def my-map {:a "fish"})
;; (get my-map "a")
;; (my-map :a)
;; (:a my-map)
