sh.shardCollection(
    "test.cities",
    {name: 1}
);

sh.splitAt(
    "test.cities",
    {name: "Mascalucia"}
);
