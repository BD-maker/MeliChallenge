package com.brunodiaz.melichallenge.data.model

fun getFakeItemList():ItemsResponse{
    return ItemsResponse("dummy",
        Page(100,50),
        listOf()
        )
}

fun getFakeItem():Item{
    return Item("MLA213123",
        "Tittle",
        1232131.0,
        "new",
        "url",
        listOf(),
        Shipping(true,"full"),
        42,
        10
    )
}

fun getFakeDescription():Description{
    return Description("dummy long text")
}