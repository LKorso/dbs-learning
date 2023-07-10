db.phones.aggregate([
    {
        $project: {
            emits: {
                key: {
                    digits: {
                        $function: {
                            body: function(number) { return [...new Set(number.toString().split(''))].sort() },
                            args: ["$components.number"],
                            lang: "js"
                        }
                    },
                    country: "$components.country"
                },
                value: { count: { $literal: 1 } }
            }
        }
    },
    {
        $unwind: "$emits"
    },
    {
        $group: {
            _id: "$emits.key",
            value: {
                $accumulator: {
                    init: function() { return {count: 0} },
                    accumulate: function(state, count) { return { count: state.count + 1 } },
                    accumulateArgs: ["$emits.value.count"],
                    merge: function(state1, state2) { return { count: state1.count + state2.count } },
                    finalize: function(state) { return { total: state.count } },
                    lang: "js"
                }
            }
        }
    },
    {
        $out: "result"
    }
])