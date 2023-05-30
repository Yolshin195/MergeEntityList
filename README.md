# MergeEntityList

version = 1.1.1

## Example
```java
        List<String> newStringList = new ArrayList<String>()
        {{
            add("Test1"); add("Test2"); add("Test3"); add("OnlyNew1"); add("OnlyNew2");
        }};

        List<String> oldStringList = new ArrayList<String>()
        {{
            add("Test1"); add("Test2"); add("Test3"); add("OnlyOld1"); add("OnlyOld2");
        }};

        new MergeEntityList<String>()
                .setLambdaGetCode(entity -> entity)
                .setLambdaOnOldAndNew((newString, oldString) -> {"your code"})
                .setLambdaOnOnlyNew(newString -> {"your code"})
                .setLambdaOnOnlyOld(oldString -> {"your code"})
                .setLambdaOnCollision((type, entityList) -> {"your code"})
                .merge(newStringList, oldStringList);
```
