### Easy-object

[![codecov](https://codecov.io/gh/EasyObject/easy-object/branch/master/graph/badge.svg?token=ML1B7SE89R)](undefined)

Library for generating random objects based on provided schema.

JDK compatibility: 1.11

### Quickstart

Basic example:
```
// 1. create a factory that generates integers in range [1, 100)
isInt(1, 100) 
        // 2. prepare factory to generate 10 objects. It's required for some internal optimizations and preparations.
        .prepare(10) 
        // 3. configure consumers. This will convert numbers to json format and pring them into console
        .consume(toStd(toJson())) 
        // 4. start generation process
        .generate();
```

Output example: `[44,72,66,85,49,98,89,57,46,5]`

Object creation example:

```
// 1. declare an object with `id` field of UUIDv4 type
isObject("id", isUUID())
        // 2. add an `index` field that is a sequence 0, 1, 2, ...
        .and("index", isIndex())
        // 3. add a field contains nested object. 
        // It's only field is calculated based on parent's object index field
        .and("nestedObject", isObject("a", isInt("(parent.index + 3) ** 2"))) 
        // 4. prepare factory to generate 2 objects. It's required for some internal optimizations and preparations.
        .prepare(2) 
        // 5. configure consumers. This will convert objects to json and print them to file
        .consume(toFile("output/fileName.txt", toJson()))
        // 6. start generation process
        .generate();
```

Output example: `[{"id":"476a088f-12f4-4dc3-6a08-8f12f44dc386","index":0,"nestedObject":{"a":9}},{"id":"bd25c549-e5b8-429b-25c5-49e5b8429ba1","index":1,"nestedObject":{"a":16}}]`

You can also consume generated objects as stream:

```
isInt()
    .prepare()
    .stream()
```
