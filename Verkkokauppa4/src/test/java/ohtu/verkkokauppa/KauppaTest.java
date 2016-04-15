package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class KauppaTest {

    Kauppa kauppa;
    Varasto varasto;
    Pankki pankki;
    Ostoskori ostoskori;
    Viitegeneraattori viitegeneraattori;

    @Before
    public void setUp() {
        varasto = mock(Varasto.class);
        pankki = mock(Pankki.class);
        ostoskori = mock(Ostoskori.class);
        viitegeneraattori = mock(Viitegeneraattori.class);
        kauppa = new Kauppa(varasto, pankki, viitegeneraattori);

        when(viitegeneraattori.uusi()).thenReturn(1).thenReturn(2);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        when(varasto.saldo(2)).thenReturn(5);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "leipä", 6));

        when(varasto.saldo(3)).thenReturn(0);
        when(varasto.haeTuote(3)).thenReturn(new Tuote(2, "leipä-b", 3));
    }

    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaan() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto("pekka", 1, "12345", "33333-44455", 5);
    }

    @Test
    public void kahdenVarastossaolevanEriTuotteenOstaminenKutsuuTilisiirtoaOikein() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto("pekka", 1, "12345", "33333-44455", 11);
    }

    @Test
    public void kahdenSamanVarastossaolevanTuotteenOstaminenKutsuuTilisiirtoaOikein() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto("pekka", 1, "12345", "33333-44455", 10);
    }

    @Test
    public void kunOstetaanKahtaEriTuotettaJoistaToinenOnLoppuTilisiirtoaKutsutaanOikein() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(3);
        kauppa.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto("pekka", 1, "12345", "33333-44455", 5);
    }

    @Test
    public void edellinnenOstosEiVaikutaUuteenOstokseenJaUudellaOstoksellaOnOmaViitenumero() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto("pekka", 1, "12345", "33333-44455", 5);

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto("pekka", 2, "12345", "33333-44455", 6);
    }

    @Test
    public void tuotteenPoistoOminaisuudenKaytonJalkeenOstoksenSummaOnOikein() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.poistaKorista(1);
        kauppa.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto("pekka", 1, "12345", "33333-44455", 5);
    }
}