<h1>Top News</h1>
<p>News app displaying daily top headlines and headlines by 7 different categories 
  (general, business, techonology, entertainment, health, sports, science) built with MVVM pattern using Retrofit, Hilt and Room
</p>

<!-- Screenshots -->
<h2>Wireframes</h2>
<p>
  TBD
</p>

<!-- Tech Stack -->
<h2>Tech Stack</h2>
<li>Minumum SDK Level: 21</li>
<ul>
  <li>Architecture
    <ul>
      <li><a href="https://developer.android.com/topic/architecture">MVVM Pattern</a>: Industry-recognized software architecure pattern supported by Google</li>
    </ul>
  </li>
  <li>Jetpack Compose
  <ul>
    <li>ViewModel: Exposes data streams as a state holder</li>
    <li>Lifecycle: Observes Android lifecycles and handle operations to a change in the lifecycle status</li>
    <li><a href="https://developer.android.com/training/data-storage/room">Room</a>: Data Object Mapping library providing an abstract layer over SQLite</li>
    <li>Navigation: Supports implementing navigation through multiple fragments</li>
    <li><a href="https://developer.android.com/training/dependency-injection/hilt-android">Hilt</a>: Dependency injection library built on top of Dagger benefit from the compile-time correctness, runtime performance, scalability, and Android Studio support </li>
   </ul>
  </li>
  <li><a href="https://developer.android.com/kotlin/coroutines">Coroutines</a>: Concurrency design pattern provided by Kotlin</li>
  <li><a href="https://developer.android.com/kotlin/flow">Flow</a>: Asynchronous data stream that sequentially emits values and completes normally or with an exception</li>
  <li><a href="https://square.github.io/retrofit/">Retrofit</a>: Type-safe REST client for Android, Java and Kotlin developed by Square.  </li>
  <li><a href="https://square.github.io/okhttp/">OkHttp</a> : 3rd party library sending and receive HTTP-based network requests built on top of the Okio library</li>
  <li>GSON</li>
  <li><a href="https://github.com/bumptech/glide">Glide</a>: Fast and efficient open source media management and image loading framework </li>
</ul>

<!-- Architecture -->
<h2>Architecture</h2>
<p>Top News app was built with Google's recommended modern app architecture - MVVM pattern. By separating multiple app components into three layers
- UI, Domain and Data, the app is scalable, maintainable and testable.</p>
<ul>
  <li>Architectural Principles</li>
    <ul>
      <li>Separations of concerns</li>
      <li>Drive UI from data models</li>
      <li>Single source of truth</li>
      <li>Unidirectional Data Flow</li>
   </ul>
</ul>
<p align="center">
   <img src="https://user-images.githubusercontent.com/57670625/221630274-a9214a21-5908-4725-b799-800197555750.jpg"/>
</p>
<p>Top News is composed with three different layers - UI layer, domain layer and data layer. Each layer has app components which have different responsibilities</p>
<h3>Architecture Overview</h3>
<p align="center">
   <img src="https://user-images.githubusercontent.com/57670625/221953743-a0aeb6ab-8bf9-45b0-bde9-59c0d3912899.jpg"/>
</p>
<p></p>
<p align="center">
   <img src="https://user-images.githubusercontent.com/57670625/221953741-1c9dd925-1dc3-47d7-a6c2-d50c2efd0fba.jpg"/>
</p>
<p></p>
<p align="center">
   <img src="https://user-images.githubusercontent.com/57670625/221953742-45a26049-c855-45e1-aba8-e69d2f166933.jpg"/>
</p>
<p></p>

<!-- Open APIs -->
<h2>Open APIs</h2>!
<p>Top News using the <a href="https://newsapi.org//">NewsAPI</a> for fetching JSON object from the server. News API provides articles and breaking news headlines from news sources and blogs across the web with JSON API.</p>
 
