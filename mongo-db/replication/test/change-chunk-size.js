db.settings.updateOne(
    {_id: "chunksize"},
    {
        $set: {
            _id: "chunksize", value: 9
        }
    },
    {upsert: true}
);