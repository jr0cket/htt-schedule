(ns htt-schedule.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(defonce app-state (atom {:text "ProgsCon Awesomenessness"
                      :schedule []}))

(defn twitter-handle [cursor component]
  (reify
    om/IRender
    (render [this]
      (dom/a #js {:href (str "https://twitter.com/" cursor)}
             (str "@" cursor)))))

;; Add twitter icon button
;; <a class="btn btn-social-icon btn-twitter">
;; <span class="fa fa-twitter"></span>
;; </a>


#_(defn talk-detail [cursor component]
  (reify
    om/IRender
    (render [this]
      (dom/div #js {:className "panel panel-primary"}
               (dom/div #js {:className "panel-heading"} (:title cursor))
               (dom/div #js {:className "panel-body"}
                        (dom/div nil (:description cursor))
                        (dom/a #js {:href (str "https://twitter.com/" (:twitter-handle cursor))} (str "@" (:twitter-handle cursor))))))))


;; Separate out the generation of the twitter handle, updating the cursor to just contain the twitter handle (saving us from extracting it in the twitter handler component)
(defn talk-detail [cursor component]
  (reify
    om/IRender
    (render [this]
      (dom/div #js {:className "panel panel-primary"}
               (dom/div #js {:className "panel-heading"} (:title cursor))
               (dom/div #js {:className "panel-body"}
                        (dom/div nil (:description cursor))
                        #_(dom/div nil
                                 (om/img #js {:src "https://twitter.com/logo.png"}))
                        (om/build twitter-handle (:twitter-handle cursor)))))))

#_(dom/div nil
           (dom/div #js {:style {:color "blue"}} (:title cursor))
           (dom/div nil (:description cursor))
           (dom/a #js {:href (str "https://twitter.com/" (:twitter-handle cursor))} (:twitter-handle cursor)))



;; Add a star button to each talk using bootstrap
;; <button type="button" class="btn btn-default btn-lg">
;; <span class="glyphicon glyphicon-star" aria-hidden="true"></span> Star
;; </button>


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


(defn add-session [cursor component]
  (reify
    om/IRender
    (render [this]
      (dom/div nil
        (dom/h3 nil "Add New Session")
        (dom/form #js {:className "form-horizontal"}
                  (dom/input #js {:type "text"
                                  :className "form-control"
                                  :placeholder "Session Title"})
                  (dom/textarea #js {:className "form-control"
                                     :placeholder "Session description"
                                     :rows "5"})
                  (dom/input #js {:type "text"
                                  :className "form-control"
                                  :placeholder "Twitter handle"})
                  (dom/button #js {:className "btn btn-primary"
                                   :onClick (fn [e]
                                              (.preventDefault e)
                                              (om/transact! cursor :entries
                                                            (fn [items]
                                                              ((fnil conj []) items
                                                               {:title "summary"
                                                                :description "date-time"
                                                                :twitter-handle "notes"}))))}
                              "Add New Session"))
               ))))


(defn root-component [app owner]
  (reify
    om/IRender
    (render [_]
      (dom/div #js {:className "container"}
               (dom/p #js {:className "jumbotron" :style #js {:fontSize "42px"} } (:text app))
               (om/build schedule app)
               (om/build add-session app)))))

;; content header options
;; jumbotron


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
