(defproject bgame "0.1.0-SNAPSHOT"
  :description "simple game from b"
  :url "no"
  :license {:name "do what the fuck you want..."
            :url "http://www.wtfpl.net/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 ;[org.clojure/tools.namespace "1.1.0"] ;refresh
                 [org.clojure/spec.alpha "0.2.194"]
                 [quil "3.1.0"]
                 [philoskim/debux "0.6.5"]            ;dbg debugger
                 [hashp "0.1.1"]                    ;debugging #p
                 [tupelo "21.07.08"]]
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "1.1.0"]
                                  [org.clojure/java.classpath "1.0.0"]
                                  [nrepl,"0.8.3"]
                                  [vlaaad/reveal "1.3.212"]]
                   :repl-options {:nrepl-middleware [vlaaad.reveal.nrepl/middleware]}
                   :plugins [[com.jakemccrary/lein-test-refresh "0.24.1"] ; lein test-refresh
                             [venantius/ultra "0.6.0"]]}})
