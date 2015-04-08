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

(defn ^:private proper-size-and-rank? [hand size rank-count]
  (let [rank-counts (vals (rank-frequencies hand))]
    (and (= size (count rank-counts))
         (not (nil? (some #{rank-count} rank-counts))))))

(defn pair? [hand]
  (proper-size-and-rank? hand 4 2))

(defn three-of-a-kind? [hand]
  (proper-size-and-rank? hand 3 3))

(defn four-of-a-kind? [hand]
  (proper-size-and-rank? hand 2 4))

(defn flush? [hand]
  (= 5 (first (vals (suit-frequencies hand)))))

(defn full-house? [hand]
  (and (proper-size-and-rank? hand 2 3)
       (proper-size-and-rank? hand 2 2)))

(defn two-pairs? [hand]
  (proper-size-and-rank? hand 3 2))

(defn straight? [hand]
  nil)

(defn straight-flush? [hand]
  nil)

(defn value [hand]
  nil)
