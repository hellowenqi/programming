this is a programming test

【question】
You are required to write a class that implements a namespace structure. 
Each node has a value and possibly children. The class has the following API which you need to implement:
create(path, value)
where path is something like /a/c. value is the value to be stored in the node. All the nodes leading up to the last one should already exist, and the last one must not exist, else create should raise an error. In the above example, that means create(/a/c/g, “foo”) should work, but create(/f/g/h, “bar”) should raise an error.
set_value(path, value)
This api sets the value of a node. If path doesn’t address a valid node in the namespace, set_value should throw an error.
get_value(path)
This API returns the value of the node represented by path. If path is invalid, then get_value should throw an error.
watch(path, callback)
watch accepts a path and a callback function pointer (class in Java). Whenever the value of path or that of any of its children are set, the callback registered for the path must be called. The callback function accepts two arguments - the path that was changed and the value that was set.


【how to run】
javac -d bin -cp lib/*:  test/com/bigdata/TreeTest.java src/com/bigdata/*
java -cp lib/*:lib/:bin/ com.bigdata.TreeTest
