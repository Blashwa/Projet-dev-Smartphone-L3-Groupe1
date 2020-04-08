package com.example.projetdevsmartphonel3

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projetdevsmartphonel3.ui.SecondActivity.ActivityTwoFragment
import com.example.projetdevsmartphonel3.ui.FirstActivity.ActivityOneFragment
import com.example.projetdevsmartphonel3.ui.ThirdActivity.ActivityThreeFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var mMap: GoogleMap
    private var points = ArrayList<Waypoint>()
    lateinit var dataFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //val dataFragment :  Fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as Fragment
        //dataFragment = HomeFragment()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
/*
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        */
        navView.setOnNavigationItemSelectedListener(this)
        openFragment(ActivityOneFragment())
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        // On initialise la carte
        mMap = googleMap

        // On change le type de la carte pour une vue satellite
        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE

        // On cree un waypoint pour La Rochelle
        val wpLaRochelle = Waypoint(46.147994, -1.169709, "Port de La Rochelle")


        //drawline()
        // On cree un marker a partir du waypoint de La Rochelle
       // val markerLaRochelle = wpLaRochelle.addMarkerToMap(mMap)
       // val markeurObjectif = wpLarochelle2.addMarkerToMap(mMap)

        // On retire le marker de La Rochelle de la carte
        //markerLaRochelle.remove()

        // La carte zoom par defaut sur le waypoint de La Rochelle
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(wpLaRochelle.coordX, wpLaRochelle.coordY), 15.0f))

        //val laRochelle = LatLng(46.147994, -1.169709)
        //mMap.addMarker(MarkerOptions().position(laRochelle).title("Port de La Rochelle"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(laRochelle, 15.0f))
    }

    fun drawline()
    {
    //fonction qui va tracer des polylines
        mMap.clear()

        val path : MutableList<List<LatLng>> = ArrayList()
        var coords : ArrayList<LatLng> = ArrayList()

        //coords = dataFragment.getcoords()
//        print(coords[0])

        for (i in 0 until points.size)
        {
            coords.add(LatLng(points[i].coordX, points[i].coordY))
        }

        path.add(coords)
        for (i in 0 until path.size) {
            mMap.addPolyline(PolylineOptions().clickable((false)).addAll(path[i]).color((Color.RED)))
        }

    }

    //fonction qui va générer des waypoints
    fun setcoords(value : ArrayList<LatLng>)
    {
        points.clear()
        for (i in 0 until value.size)
        {
            points.add(Waypoint(value[i].latitude, value[i].longitude, ""))
        }

        drawline()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.navigation_first->{
                val fragment = ActivityOneFragment()
                openFragment(fragment)
                return true
            }
            R.id.navigation_second->{
                val fragment = ActivityTwoFragment()
                openFragment(fragment)
                return true
            }
            R.id.navigation_third->{
                val fragment = ActivityThreeFragment()
                openFragment(fragment)
                return true
            }
        }
        return false
    }

    private fun openFragment(fragment : Fragment):Boolean{
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit()
        return true
    }

}
