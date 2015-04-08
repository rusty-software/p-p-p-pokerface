(ns p-p-p-pokerface)

(defn rank [card]
  (let [[r _] card
        upper-ranks {\T 10 \J 11 \Q 12 \K 13 \A 14}]
    (if (Character/isDigit r)
      (Integer/valueOf (str r))
      (get upper-ranks r))))

(defn suit [card]
  (let [[_ s] card]
    (str s)))

(defn ^:private kv-frequencies [kv hand]
  (frequencies (map kv hand)))

(defn ^:private rank-frequencies [hand]
  (kv-frequencies rank hand))

(defn ^:private suit-frequencies [hand]
  (kv-frequencies suit hand))

(defn ^:private rank-frequency-of? [freq hand]
  (= freq (apply max (vals (rank-frequencies hand)))))

(defn pair? [hand]
  (rank-frequency-of? 2 hand))

(defn three-of-a-kind? [hand]
  (rank-frequency-of? 3 hand))

(defn four-of-a-kind? [hand]
  (rank-frequency-of? 4 hand))

(defn flush? [hand]
  (= 1 (first (vals (suit-frequencies hand)))))

(defn full-house? [hand]
  (and (pair? hand)
       (three-of-a-kind? hand)))

(defn two-pairs? [hand]
  nil)

(defn straight? [hand]
  nil)

(defn straight-flush? [hand]
  nil)

(defn value [hand]
  nil)
