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
                value: { count: {$literal: 1} }
            }
        }
    },
    {
        $unwind: "$emits"
    },
    {
        $group: {
            _id: "$emits.key",
            total: {
                $sum: "$emits.value.count"
            }
        }
    },
    {
        $out: "resultSum"
    }
])