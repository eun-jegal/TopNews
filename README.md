<h1>Top News</h1>
<p>News app displaying daily top headlines and headlines by 7 different categories 
  (general, business, techonology, entertainment, health, sports, science) built with MVVM pattern using Retrofit, Hilt and Room
</p>
<br>

<!-- Screenshots -->
<h2>Screenshots</h2>
<p align="center">
  <img src="https://user-images.githubusercontent.com/57670625/224512417-104f7d7f-d93a-491a-a6c3-09a0144466de.png" width="23%"/>
  &nbsp;                                                                                                                
  <img src="https://user-images.githubusercontent.com/57670625/224512416-035e48c5-18cc-449f-93a5-6cb5f92f65a3.png" width="23%"/>
  &nbsp;
  <img src="https://user-images.githubusercontent.com/57670625/224512415-ebec77e8-f55a-4842-9346-de5c8ec8594c.png" width="23%"/>
  &nbsp;
  <img src="https://user-images.githubusercontent.com/57670625/224512414-a6d9ea3b-5762-4952-a6b6-57a26b34e4aa.png" width="23%"/>
</p>
<br>

<!-- Tech Stack -->
<h2>Tech Stack</h2>
<ul>
<li>Minumum SDK Level: 21</li>
<li>100% Kotlin</li>
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
<li><a href="https://square.github.io/retrofit/">Retrofit</a>: Type-safe REST client for Android, Java and Kotlin developed by Square.  </li>
<li><a href="https://square.github.io/okhttp/">OkHttp</a> : 3rd party library sending and receive HTTP-based network requests built on top of the Okio library</li>
<li><a href="https://github.com/google/gson">GSON</a>: Java library that can be used to convert Java Objects into their JSON representation</li>
<li><a href="https://github.com/bumptech/glide">Glide</a>: Fast and efficient open source media management and image loading framework </li>
</ul>
<br>

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
   <img src="https://user-images.githubusercontent.com/57670625/224512399-9515be20-a3e7-462a-9a8e-120c3511adab.jpg"/>
</p>

<h3>Architecture Overview</h3>
<p>Top News is composed with three different layers - UI layer, domain layer and data layer. Each layer has app components which have different responsibilities.</p>

<h3>UI Layer</h3>
<p align="center">
   <img src="https://user-images.githubusercontent.com/57670625/222527163-ff9135ae-428a-4306-a10e-37b76066cbc9.jpg"/>
</p>
<p>UI layer displays the application data and serves as the primary point for user interactions. Whenever the app data changes, the UI should update to reflect changes made by either user interaction or external input.</p>
<ul>
  <li>The main activity and all the fragments - Feed, Browse, Saved, etc are UI elements and they display articles received from network requests and the database</li>
  <li>NewsViewModel holds state and plays as a bridge between UI elements and the data layer</li>
  <li>UI elements request actions to ViewModel and observer ViewModel's livedatas to automatically update screens</li>
</ul>

<h3>Domain Layer</h3>
<p align="center">
   <img src="https://user-images.githubusercontent.com/57670625/222527239-0513a887-4d69-408d-9e51-87093c4f8aaa.jpg"/>
</p>
<p>Domain layer is optional layer for the modern architectural pattern. However, when the app is a large scale app and has complex functionalities, the domain layer helps to avoid code duplications and improves readability of codes. Top News has seven main use cases and these classes fit between ViewModel and the data layer(Repository).</p>

<h3>Data Layer</h3>
<p align="center">
   <img src="https://user-images.githubusercontent.com/57670625/222527292-cd2b4364-a622-4354-bb46-cbd46a9ebcf8.jpg"/>
</p>
<p>Data layer is reponsible for containing application data and business logics. The data layer is consisted of repositories and data sources. It is important to keep each repository as a single source of truth.</p>
<ul>
  <li>NewsRepository is a single source of truth and requests data from NewsLocalDataSource and NewsRemoteDataSource.</li>
  <li>NewsLocalDataSource is a class managing the database built with Room library and NewsRemoteDataSource is a class requesting network response to NewsAPI server.</li>
</ul>
<br>

<!-- Open APIs -->
<h2>Open APIs</h2>
<p>Top News using the <a href="https://newsapi.org//">NewsAPI</a> for fetching JSON object from the server. News API provides articles and breaking news headlines from news sources and blogs across the web with JSON API.</p>
 
