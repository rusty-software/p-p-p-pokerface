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

(defn- kv-frequencies [kv hand]
  (frequencies (map kv hand)))

(defn- rank-frequencies [hand]
  (kv-frequencies rank hand))

(defn- suit-frequencies [hand]
  (kv-frequencies suit hand))

(defn- proper-size-and-rank? [hand size rank-count]
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

(defn- maybe-replace-ace [ranks]
  (sort (replace {14 1} ranks)))

(defn straight? [hand]
  (let [ordered-hand-ranks (sort (map rank hand))
        low-rank (apply min ordered-hand-ranks)
        expected-straight (range low-rank (+ low-rank 5))
        low-straight (range 1 6)]
    (or (= ordered-hand-ranks expected-straight)
        (= (maybe-replace-ace ordered-hand-ranks) low-straight))))

(defn straight-flush? [hand]
  (and (straight? hand) (flush? hand)))

(defn value [hand]
  (cond
    (straight-flush? hand) 8
    (four-of-a-kind? hand) 7
    (full-house? hand) 6
    (flush? hand) 5
    (straight? hand) 4
    (three-of-a-kind? hand) 3
    (two-pairs? hand) 2
    (pair? hand) 1
    :else 0))
