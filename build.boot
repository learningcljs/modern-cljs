(set-env!
 :source-paths #{"src/cljs"}
 :resource-paths #{"html"}

 :dependencies '[
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [adzerk/boot-cljs "1.7.170-3"]
                 [pandeiro/boot-http "0.7.0"]
                 [adzerk/boot-reload "0.4.2"]
                 [adzerk/boot-cljs-repl "0.3.0"]
                 [com.cemerick/piggieback "0.2.1"]     ;; needed by bREPL 
                 [weasel "0.7.0"]                      ;; needed by bREPL
                 [org.clojure/tools.nrepl "0.2.12"]    ;; needed by bREPL
                 [crisptrutski/boot-cljs-test "0.2.1-SNAPSHOT"]
                ])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[pandeiro.boot-http :refer [serve]]
         '[adzerk.boot-reload :refer [reload]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[crisptrutski.boot-cljs-test :refer [test-cljs]])

(deftask testing
  "Add test/cljc for CLJ/CLJS testing purpose"
  []
  (set-env! :source-paths #(conj % "test/cljs"))
  identity)

(deftask tdd
  "Launch a IFDE and TDD Environment"
  []
  (comp
   (serve :reload true)
   (testing)
   (watch)
   (reload)
   (cljs-repl)
   (test-cljs 
              :out-file "js/main.js"
              :js-env :phantom
              :namespaces '#{modern-cljs.core-test}
              :update-fs? true)
   (target :dir #{"target"})))

;; (deftask tdd
;;   "Launch a TDD Environment"
;;   []
;;   (comp
;;    (testing)
;;    (watch)
;;    (test-cljs :update-fs? true :js-env :phantom :namespaces '#{modern-cljs.core-test})
;;    (target :dir #{"target"})))

;; define dev task as composition of subtasks
(deftask dev
  "Launch Immediate Feedback Development Environment"
  []
  (comp 
   (serve :dir "target")
   (watch)
   (reload)
   (cljs-repl) ;; before cljs task
   (cljs)
   (target :dir #{"target"})))
