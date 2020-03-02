#  Android App 開發方法熟悉
##  重點目標
1. 會 Unit Test 
以下我自創一個 Htoa類別 用來解釋 UnitTest

可以發現src資料夾下有，main/java/ChangeChar/Htoa.java 這類別
它是會改變字串，將H 轉為 a，而遇到傳入非H的字串，則直接輸出那個傳入的字串

那該如何Test這函數呢?

在src/test/java/ChangeChar/HtoaTest.java 會匯入這類別，接著進行測試。

2. 會 TDD 測試驅動開發方法
這方法就是你要先寫測試的方法，再去寫該類別，
例如要做一個連接網頁的方法，則要先思考連接網頁成功時會回傳 200 而寫下以下Test
assertEquals("200", your_connet_web_function("http://google.com") )，
接著再去實作 your_connet_web_function( ) 方法。

3. 探索 這IDE 的功能，例如左邊structure 可以看到類別內的所有函數(常用)。
 



