package com.yolshin.merge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class MergeEntityListTests {

    @Test
    void test() {
        AtomicInteger countNew = new AtomicInteger();
        AtomicInteger countOld = new AtomicInteger();
        AtomicInteger countNewAndOld = new AtomicInteger();

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
                .setLambdaOnOldAndNew((newString, oldString) -> countNewAndOld.getAndIncrement())
                .setLambdaOnOnlyNew(newString -> countNew.getAndIncrement())
                .setLambdaOnOnlyOld(oldString -> countOld.getAndIncrement())
                .merge(newStringList, oldStringList);

        Assertions.assertEquals(3,countNewAndOld.get());
        Assertions.assertEquals(2,countNew.get());
        Assertions.assertEquals(2,countOld.get());
    }

    @Test
    void testOnlyNew() {
        AtomicInteger countNew = new AtomicInteger();
        AtomicInteger countOld = new AtomicInteger();
        AtomicInteger countNewAndOld = new AtomicInteger();

        List<String> newStringList = new ArrayList<String>()
        {{
            add("Test1"); add("Test2"); add("Test3"); add("OnlyNew1"); add("OnlyNew2");
        }};

        List<String> oldStringList = new ArrayList<String>()
        {{
            add("Test1"); add("Test2"); add("Test3");
        }};

        new MergeEntityList<String>()
                .setLambdaGetCode(entity -> entity)
                .setLambdaOnOldAndNew((newString, oldString) -> countNewAndOld.getAndIncrement())
                .setLambdaOnOnlyNew(newString -> countNew.getAndIncrement())
                .setLambdaOnOnlyOld(oldString -> countOld.getAndIncrement())
                .merge(newStringList, oldStringList);

        Assertions.assertEquals(3,countNewAndOld.get());
        Assertions.assertEquals(2,countNew.get());
        Assertions.assertEquals(0,countOld.get());
    }

    @Test
    void testOnlyOld() {
        AtomicInteger countNew = new AtomicInteger();
        AtomicInteger countOld = new AtomicInteger();
        AtomicInteger countNewAndOld = new AtomicInteger();

        List<String> newStringList = new ArrayList<String>()
        {{
            add("Test1"); add("Test2"); add("Test3");
        }};
        List<String> oldStringList = new ArrayList<String>()
        {{
            add("Test1"); add("Test2"); add("Test3"); add("OnlyOld1"); add("OnlyOld2");
        }};

        new MergeEntityList<String>()
                .setLambdaGetCode(entity -> entity)
                .setLambdaOnOldAndNew((newString, oldString) -> countNewAndOld.getAndIncrement())
                .setLambdaOnOnlyNew(newString -> countNew.getAndIncrement())
                .setLambdaOnOnlyOld(oldString -> countOld.getAndIncrement())
                .merge(newStringList, oldStringList);

        Assertions.assertEquals(3,countNewAndOld.get());
        Assertions.assertEquals(0,countNew.get());
        Assertions.assertEquals(2,countOld.get());
    }

    @Test
    void testOldAndNew() {
        AtomicInteger countNew = new AtomicInteger();
        AtomicInteger countOld = new AtomicInteger();
        AtomicInteger countNewAndOld = new AtomicInteger();

        List<String> newStringList = new ArrayList<String>()
        {{
            add("OnlyNew1"); add("OnlyNew2");
        }};

        List<String> oldStringList = new ArrayList<String>()
        {{
            add("OnlyOld1"); add("OnlyOld2");
        }};

        new MergeEntityList<String>()
                .setLambdaGetCode(entity -> entity)
                .setLambdaOnOldAndNew((newString, oldString) -> countNewAndOld.getAndIncrement())
                .setLambdaOnOnlyNew(newString -> countNew.getAndIncrement())
                .setLambdaOnOnlyOld(oldString -> countOld.getAndIncrement())
                .merge(newStringList, oldStringList);

        Assertions.assertEquals(0,countNewAndOld.get());
        Assertions.assertEquals(2,countNew.get());
        Assertions.assertEquals(2,countOld.get());
    }

    @Test
    void testIsEmpty() {
        AtomicInteger countNew = new AtomicInteger();
        AtomicInteger countOld = new AtomicInteger();
        AtomicInteger countNewAndOld = new AtomicInteger();

        List<String> newStringList = new ArrayList<>();
        List<String> oldStringList = new ArrayList<>();

        new MergeEntityList<String>()
                .setLambdaGetCode(entity -> entity)
                .setLambdaOnOldAndNew((newString, oldString) -> countNewAndOld.getAndIncrement())
                .setLambdaOnOnlyNew(newString -> countNew.getAndIncrement())
                .setLambdaOnOnlyOld(oldString -> countOld.getAndIncrement())
                .merge(newStringList, oldStringList);

        Assertions.assertEquals(0,countNewAndOld.get());
        Assertions.assertEquals(0,countNew.get());
        Assertions.assertEquals(0,countOld.get());
    }

    @Test
    void testNewIsEmpty() {
        AtomicInteger countNew = new AtomicInteger();
        AtomicInteger countOld = new AtomicInteger();
        AtomicInteger countNewAndOld = new AtomicInteger();

        List<String> newStringList = new ArrayList<>();
        List<String> oldStringList = new ArrayList<String>()
        {{
            add("Test1"); add("Test2"); add("Test3"); add("OnlyOld1"); add("OnlyOld2");
        }};

        new MergeEntityList<String>()
                .setLambdaGetCode(entity -> entity)
                .setLambdaOnOldAndNew((newString, oldString) -> countNewAndOld.getAndIncrement())
                .setLambdaOnOnlyNew(newString -> countNew.getAndIncrement())
                .setLambdaOnOnlyOld(oldString -> countOld.getAndIncrement())
                .merge(newStringList, oldStringList);

        Assertions.assertEquals(0,countNewAndOld.get());
        Assertions.assertEquals(0,countNew.get());
        Assertions.assertEquals(5,countOld.get());
    }

    @Test
    void testOldIsEmpty() {
        AtomicInteger countNew = new AtomicInteger();
        AtomicInteger countOld = new AtomicInteger();
        AtomicInteger countNewAndOld = new AtomicInteger();

        List<String> newStringList = new ArrayList<String>()
        {{
            add("Test1"); add("Test2"); add("Test3"); add("OnlyNew1"); add("OnlyNew2");
        }};

        List<String> oldStringList = new ArrayList<>();

        new MergeEntityList<String>()
                .setLambdaGetCode(entity -> entity)
                .setLambdaOnOldAndNew((newString, oldString) -> countNewAndOld.getAndIncrement())
                .setLambdaOnOnlyNew(newString -> countNew.getAndIncrement())
                .setLambdaOnOnlyOld(oldString -> countOld.getAndIncrement())
                .merge(newStringList, oldStringList);

        Assertions.assertEquals(0,countNewAndOld.get());
        Assertions.assertEquals(5,countNew.get());
        Assertions.assertEquals(0,countOld.get());
    }

    @Test
    void testCollision() {
        AtomicInteger countNew = new AtomicInteger();
        AtomicInteger countOld = new AtomicInteger();
        AtomicInteger countNewAndOld = new AtomicInteger();

        List<String> newStringList = new ArrayList<String>()
        {{
            add("Test1"); add("Test2"); add("Test3"); add("OnlyNew1"); add("OnlyNew2");
            add("TestCollision");add("TestCollision");add("TestCollision");
            add("TestCollisionOnlyNew");add("TestCollisionOnlyNew");
        }};

        List<String> oldStringList = new ArrayList<String>()
        {{
            add("Test1"); add("Test2"); add("Test3"); add("OnlyOld1"); add("OnlyOld2");
            add("TestCollision");add("TestCollision");add("TestCollision");
            add("TestCollisionOnlyOld");add("TestCollisionOnlyOld");
        }};

        new MergeEntityList<String>()
                .setLambdaGetCode(entity -> entity)
                .setLambdaOnOldAndNew((newString, oldString) -> countNewAndOld.getAndIncrement())
                .setLambdaOnOnlyNew(newString -> countNew.getAndIncrement())
                .setLambdaOnOnlyOld(oldString -> countOld.getAndIncrement())
                .setLambdaOnCollision((type, entityList) -> {
                    if (Objects.equals(type, MergeEntityList.Type.NEW)) {
                        return entityList.get(0);
                    }
                    if (Objects.equals(type, MergeEntityList.Type.OLD)) {
                        return entityList.get(0);
                    }
                    throw new RuntimeException("Something went wrong during collision handling!");
                })
                .merge(newStringList, oldStringList);

        Assertions.assertEquals(4,countNewAndOld.get());
        Assertions.assertEquals(3,countNew.get());
        Assertions.assertEquals(3,countOld.get());
    }

    @Test
    void testCollisionWithoutLambdaOnCollision() {
        AtomicInteger countNew = new AtomicInteger();
        AtomicInteger countOld = new AtomicInteger();
        AtomicInteger countNewAndOld = new AtomicInteger();

        List<String> newStringList = new ArrayList<String>()
        {{
            add("Test1"); add("Test2"); add("Test3"); add("OnlyNew1"); add("OnlyNew2");
            add("TestCollision");add("TestCollision");add("TestCollision");
            add("TestCollisionOnlyNew");add("TestCollisionOnlyNew");
        }};

        List<String> oldStringList = new ArrayList<String>()
        {{
            add("Test1"); add("Test2"); add("Test3"); add("OnlyOld1"); add("OnlyOld2");
            add("TestCollision");add("TestCollision");add("TestCollision");
            add("TestCollisionOnlyOld");add("TestCollisionOnlyOld");
        }};

        new MergeEntityList<String>()
                .setLambdaGetCode(entity -> entity)
                .setLambdaOnOldAndNew((newString, oldString) -> countNewAndOld.getAndIncrement())
                .setLambdaOnOnlyNew(newString -> countNew.getAndIncrement())
                .setLambdaOnOnlyOld(oldString -> countOld.getAndIncrement())
                .merge(newStringList, oldStringList);

        Assertions.assertEquals(4,countNewAndOld.get());
        Assertions.assertEquals(3,countNew.get());
        Assertions.assertEquals(3,countOld.get());
    }
}
