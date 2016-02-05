package com.example.ejemplogeoreferenciacion;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		OnMapClickListener {

	/*
	 * para cambiar sólo el nivel de zoom podremos utilizar los siguientes
	 * métodos para crear nuestro CameraUpdate:
	 * 
	 * CameraUpdateFactory.zoomIn(). Aumenta en 1 el nivel de zoom.
	 * CameraUpdateFactory.zoomOut(). Disminuye en 1 el nivel de zoom.
	 * CameraUpdateFactory.zoomTo(nivel_de_zoom). Establece el nivel de zoom.
	 * Por su parte, para actualizar sólo la latitud-longitud de la cámara
	 * podremos utilizar:
	 * 
	 * CameraUpdateFactory.newLatLng(lat, long). Establece la lat-lng expresadas
	 * en grados. Si queremos modificar los dos parámetros anteriores de forma
	 * conjunta, también tendremos disponible el método siguiente:
	 * 
	 * CameraUpdateFactory.newLatLngZoom(lat, long, zoom). Establece la lat-lng
	 * y el zoom. Para movernos lateralmente por el mapa (panning) podríamos
	 * utilizar los métodos de scroll:
	 * 
	 * CameraUpdateFactory.scrollBy(scrollHorizontal, scrollVertical). Scroll
	 * expresado en píxeles.
	 */

	private GoogleMap mapa;

	private final LatLng EAM = new LatLng(4.541763, -75.663464); // posicion de
																	// la EAM

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mapa = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();// se referencia el fragment

		mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE); // tipo de mapa (normal,
														// satélite, hibrido o
														// relieve)
		mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(EAM, 15));// Ubica el
																	// mapa en
																	// la EAM,
																	// con zoom
																	// de 15 ( 2
																	// (continente)
																	// hasta 21
																	// (calle))
		mapa.setMyLocationEnabled(true);// activa la visualización de la
										// posición del dispositivo por medio
										// del típico circulo azul

		mapa.getUiSettings().setZoomControlsEnabled(false);// se desactiva los
															// botones de zoom
		mapa.getUiSettings().setCompassEnabled(true);// visualizar la brujula

		mapa.addMarker(new MarkerOptions().position(EAM)// posicion del marcador
				.title("EAM")// titulo del marcador
				.snippet("Escuela de administracion y mercadotecnia")
				// descripcion
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))// icono
																			// marcador
				.anchor(0.5f, 0.5f));// tamaño

		mapa.setOnMapClickListener(this);// se asigna el lister asignado al
											// metodo onMapClick del mapa

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void dibujarLineas(View view) {
		PolygonOptions rectangulo = new PolygonOptions().add(new LatLng(45.0,
				-12.0), new LatLng(45.0, 5.0), new LatLng(34.5, 5.0),
				new LatLng(34.5, -12.0));
		
		

		rectangulo.strokeWidth(8);
		rectangulo.strokeColor(Color.RED);

		mapa.addPolygon(rectangulo);
		
		mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.0, -12.0), 5));
		// se actualiza la posicion en pantalla
	}

	public void moveCamera(View view) {
		mapa.moveCamera(CameraUpdateFactory.newLatLng(EAM));// se actualiza la
															// posicion en la
															// pantalla
	}

	public void mostrarTerreno(View view) {
		mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
	}

	public void mostrarHibrido(View view) {
		mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	}

	public void mostrarNormal(View view) {
		mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	}

	public void miPosicionActual(View view) {

		if (mapa.getMyLocation() != null) {

			double latitudActual = mapa.getMyLocation().getLatitude(); // determina
																		// la
																		// latitud
																		// actual
			double longitudActual = mapa.getMyLocation().getLongitude();// determina
																		// la
																		// longitud
																		// actual

			mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					latitudActual, longitudActual), 15));// actualiza la
															// posicion del mapa
															// animadamente
		}
	}

	public void addMarker(View view) {

		double latitudMapa = mapa.getCameraPosition().target.latitude;// latitud
																		// del
																		// punto
																		// central
																		// que
																		// se
																		// este
																		// visualizando
		double altitudMapa = mapa.getCameraPosition().target.longitude; // altitud
																		// del
																		// punto
																		// central
																		// que
																		// se
																		// este
																		// visualizando

		mapa.addMarker(new MarkerOptions().position(new LatLng(latitudMapa,
				altitudMapa)));

	}

	public void vista3D(View view) {
		LatLng madrid = new LatLng(40.417325, -3.683081);
		CameraPosition camPos = new CameraPosition.Builder().target(madrid) // Centramos
																			// el
																			// mapa
																			// en
																			// Madrid
				.zoom(19) // Establecemos el zoom en 19
				.bearing(45) // Establecemos la orientación con el noreste
								// arriba
				.tilt(70) // Bajamos el punto de vista de la cámara 70 grados
				.build();

		mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);

		mapa.animateCamera(camUpd3);
	}

	public void posActual(View view) {

		CameraPosition camPos = mapa.getCameraPosition();

		LatLng coordenadas = camPos.target;
		double latitud = coordenadas.latitude;
		double longitud = coordenadas.longitude;

		Toast.makeText(getApplicationContext(),
				"Longitud " + longitud + "\n" + " Latitud " + latitud,
				Toast.LENGTH_SHORT).show();

		/*
		 * float zoom = camPos.zoom; //calcular zoom actual float orientacion =
		 * camPos.bearing; //calcular orientacion actual float angulo =
		 * camPos.tilt; //calcular el angulo actual.
		 */

	}

	@Override
	public void onMapClick(LatLng puntoPulsado) {

		// Recibe por parametro la posicion exacta donde se pulso y añade un
		// marcador
		mapa.addMarker(new MarkerOptions().position(puntoPulsado).icon(
				BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
	}

}
