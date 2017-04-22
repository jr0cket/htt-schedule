(ns htt-schedule.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [htt-schedule.core-test]
   [htt-schedule.common-test]))

(enable-console-print!)

(doo-tests 'htt-schedule.core-test
           'htt-schedule.common-test)
