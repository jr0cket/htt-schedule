(ns htt-schedule.talks)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Define a number of talks in the code so you can see the data structure used
;;
;; In practice talk data would be pulled from an API, files or datastore

;; TODO: Add spec for the talks, to ensure they have been created correctly


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Real ProgsCon sessions

;; Defining Trisha Gee's talk
(def trisha-gee
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



(def michael-nitschinger
  {:title "The Walking Dead – A Survival Guide To Resilient Reactive Applications"
   :bio
   "Michael is a Developer Advocate for Couchbase, Inc. He is part of the engineering team, the release manager of the Couchbase Java SDK and responsible for enterprise framework integration (like Spring Data or Hibernate)."
   :description
   "The more you sweat in peace, the less you bleed in war – the US marines certainly know how to deal with the unexpected. Building resilient distributed applications is not an easy task and you better prepare for failure during development.

In this talk you will learn how to build event-driven applications that are resilient from the bottom up, allowing you to deal with remote services that are failing, slow or misbehaving.

We’ll look at code and patterns that have been assembled to withstand failure, react to load spikes and have been proven in production.

Even if you are just consuming data from a database over the network, this talk is for you."
   :twitter-handle "daschl"})






;; Other ideas

;; Slurp the progscon schedule into the app from a file (or scrapped from the website for complete awesomenessness)



;; Media heading with twitter profile picture

;; ? Given a twitter handle, how do we consistently get the profile picture (without having to use the twitter API)
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


#_(defn talk-detail [cursor component]
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


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Parens are easy

#_(str "#" (str "I" (str "love" (str "parens"))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; naming convention for channels ?

;; (def  |delete "fish")

;; |delete



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Alternative talk layout using bootstrap media component
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
