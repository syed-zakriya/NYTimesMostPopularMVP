# NYTimesMostPopularMVP
<p>This is a simple android project which fetches the most popular/viewed articles using the NY Times web API.</p>

<b>The aplication is based on the Model-View-Presenter(MVP) architecture.</b><br />
<ul>
  <li>Model(M)- MostViewedModel.java</li>
  <li>View(V)- MostPopularActivity.java</li>
  <li>Presenter(P)-MostPopularPresenterImpl.java</li>
</ul>
<p>The API calls are made using the Volley Library (Singleton Design Pattern is used here).
The data fetched from this API is displayed in a RecyclerView. The search button in the toolbar can be used to search the articles based on the Title of the articles. The search option used here is a SearchView i.e the search button on the toolbar is casted to a searchview. The actual search is based on the Filter concept in adapter.</p>
<p>
  <b>Steps for running the code in Emulator:</b>
  <ul>
    <li>Open Android Studio</li>
    <li>Select the option "Open an existing Android Studio project"</li>
    <li>Select the project <i><b>"NYTimesSample"</b></i> from the path where you have downloaded the code in your local machine</li>
    <li>Once the project is synced, launch an Emulator or connect the Android device for testing</li>
    <li>From the toolbar goto <b>"Run > Run'app'"</b> and select the connected device or Emulator to run the code </li>
  </u>
</p>

<p align="center">
  <img src="/docs/screenshots/1.png" title="Most popular article list">
  <img src="/docs/screenshots/2.png" title="Search in toolbar">
</p>
