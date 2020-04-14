package com.livetree.ecransomel.back.Dao;

import com.livetree.ecransomel.back.Entities.TrendTable_Jour;
import com.livetree.ecransomel.back.Entities.TrendTable_api;
import com.livetree.ecransomel.back.Reporitory.TrendTable_JourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TrendDao {
    Logger log = LoggerFactory.getLogger(TestLog.class);

    public TrendDao(TrendTable_JourRepository trendTable_jourRepository) {
        this.trendTable_jourRepository = trendTable_jourRepository;
    }

    /**
     * Ces listes permettent d'enregistrer les informations stockés dans la base de donnée
     */
    public static final List<String> heiProd = List.of("HEI_PV.Energies.EAtot_neg");
    public static final List<String> heiCons = List.of("HEI_13RT.Energies.EAtot_pos", "HEI_5RNS.Energies.EAtot_pos");
    public static final List<String> haCons = List.of("HA.Energies.EAtot_pos");
    public static final List<String> rizommeProd = List.of("Rizomm_PV.EnergiesI60.EAtot_neg");
    public static final List<String> rizommeCons = List.of("Rizomm_TGBT. Energies.EAtot_pos");


    private final TrendTable_JourRepository trendTable_jourRepository;

    /**
     * Api test, affiche le contenu de la bdd
     *
     * @return
     */
    @GetMapping("/test")
    public ArrayList<TrendTable_Jour> getAll() {

        return trendTable_jourRepository.findAll();
    }


    /**
     * Cette fonction relance l'historique sur une période donnée
     *
     * @param building
     * @param startDate jour de début, au format dd.MM.yyyy
     * @param endDate   jour de fin, au format dd.MM.yyyy
     * @return
     * @throws ParseException
     */
    @GetMapping("gethistory/{building}/{startDate}/{endDate}")
    public ArrayList<TrendTable_api> getListOfDayInformation(@PathVariable String building, @PathVariable String startDate, @PathVariable String endDate) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        Date date1 = dateFormat.parse(startDate);
        Date date2 = dateFormat.parse(endDate);

        ArrayList<Date> liste = getDaysBetweenDates(date1, date2);
        ArrayList<TrendTable_api> trendTable_apis = new ArrayList<TrendTable_api>();
        for (Date date : liste) {
            trendTable_apis.add(getDayInformations(dateFormat.format(date), building));

        }
        return trendTable_apis;
    }

    @ModelAttribute
    public void setReponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT");
    }

    /**
     * Cette fonction permet de creer un objet TrendTable_api
     *
     * @param day      jour, au format dd.MM.yyyy
     * @param building nom du batiment
     * @return
     * @throws ParseException
     */
    @GetMapping("getday/{building}/{day}")
    public TrendTable_api getDayInformations(@PathVariable String day, @PathVariable String building) throws ParseException {
        log.info("______________Recuperation des informations de consomation d'un batiment pour une journée____________");
        Date date1 = new SimpleDateFormat("dd.MM.yyyy" + "hh:mm").parse(day + "08:00");
        log.info("Date du jour, matin : " + date1);
        log.info("Batiment: " + building);

        Instant instant = date1.toInstant();
        long fileTimeMatin = fromInstant(instant);


        //On initialise l'objet qui va etre retourné
        TrendTable_api trendTable_api = new TrendTable_api();


        trendTable_api.setNomBatiment(building);
        trendTable_api.setConsumption(0);
        trendTable_api.setProduction(0);

        ArrayList<String> listProd = new ArrayList<>();
        ArrayList<String> listCons = new ArrayList<>();

        //Permet de renseigner quelle liste on va parcourir.
        switch (building) {
            case "HEI": {

                listProd.addAll((heiProd));
                listCons.addAll(heiCons);

                break;
            }
            case "HA": {
                listCons.addAll(haCons);

                break;
            }
            case "Rizomm": {
                listProd.addAll((rizommeCons));
                listCons.addAll(rizommeProd);
                break;
            }


        }
        log.info("Liste des appareils que l'on va interroger " + listCons.toString() + " " + listCons.toString());


        System.out.println(listCons);

        //Pour connaitre la consommation d'un batiment, on effectue la soustraction de la valeur de la production du soir moins celle du matin

        log.info(" ________________Consommation___________-");

        for (String name : listCons) {
            log.info("Ajout de la consomation pour " + building + " depuis le capteur  " + name);

            //Initialisation pour la consomdmation
            TrendTable_Jour trendTable_jour_matin = new TrendTable_Jour(building);
            TrendTable_Jour trendTable_jour_soir = new TrendTable_Jour(building);

            //Test de présence dans la base de donnée du matin
            if (trendTable_jourRepository.findFirstByChronoBetweenAndName(fileTimeMatin, fileTimeMatin + 6_000_000_000L, name) != null) {
                {
                    trendTable_jour_matin = trendTable_jourRepository.findFirstByChronoBetweenAndName(fileTimeMatin, fileTimeMatin + 6_000_000_000L, name);

                }
                trendTable_jour_matin = trendTable_jourRepository.findFirstByChronoBetweenAndName(fileTimeMatin, fileTimeMatin + 6_000_000_000L, name);


                log.info("Valeur au matin entree 8h et 8h05" + trendTable_jour_matin.getValue());

            }

            //Recuperation de l'objet trendtable_jour soir
            Date dateNow = new Date(System.currentTimeMillis() - 5 * 60);
            //Si l'heure du systeme est avant 19h30, on recupere l'heure actuelle
            if (dateNow.compareTo(new SimpleDateFormat("dd.MM.yyyy" + "hh:mm").parse(day + "19:30")) <= 0) {
                Instant instant1 = dateNow.toInstant();
//                System.out.println(instant1);
                long duree2 = fromInstant(instant1);
                System.out.println("vox 1");
                if (trendTable_jourRepository.findFirstByChronoBetweenAndName(duree2, duree2 + 6_000_000_000L, name) != null) {
                    trendTable_jour_soir = trendTable_jourRepository.findFirstByChronoBetweenAndName(duree2, duree2 + 6_000_000_000L, name);

                    log.info("Valeur au soir entre à l'heure du systeme(avant 19h30)" + trendTable_jour_soir.getValue());
                }

            } else {
                Date date = new SimpleDateFormat("dd.MM.yyyy" + "hh:mm").parse(day + "19:25");
                Instant instant1 = date.toInstant();
                long duree2 = fromInstant(instant1);
                System.out.println(instant1);
                System.out.println("vox 2");
                if (trendTable_jourRepository.findFirstByChronoBetweenAndName(duree2, duree2 + 6_000_000_000L, name) != null) {
                    trendTable_jour_soir = trendTable_jourRepository.findFirstByChronoBetweenAndName(duree2, duree2 + 6_000_000_000L, name);

                    System.out.println("Valeur au soir entre à l'heure du systeme(avant 19h30)" + trendTable_jour_soir.getValue());

                } else {
                    log.warn("Erreur pour recuperer la valeur actuelle de la consomation au soir de" + name);
                }
            }
            System.out.println(trendTable_jour_soir.getValue() + "lo");
            System.out.println(trendTable_jour_matin.getId());
            log.info("Calcul effecué: "+trendTable_jour_soir.getValue() + "-" + trendTable_jour_matin.getValue() + "(+  autres valeurs" + trendTable_api.getConsumption());
            trendTable_api.setConsumption(trendTable_jour_soir.getValue() - trendTable_jour_matin.getValue() + trendTable_api.getConsumption());
        }

        //Pour connaitre la production d'un batiment, on effectue la soustraction de la valeur de la production du soir moins celle du matin




        log.info(" ________________Production___________-");
        for (String name : listProd) {
            //Initialisation des objets representant le matin et le soir pour la production
            System.out.println("Ajout de la production pour " + building + "depuis le capteur  " + name);

            TrendTable_Jour trendTable_jour_matin = new TrendTable_Jour();
            TrendTable_Jour trendTable_jour_soir = new TrendTable_Jour();

            if (trendTable_jourRepository.findFirstByChronoBetweenAndName(fileTimeMatin, fileTimeMatin + 6_000_000_000L, name) != null) {
                trendTable_jour_matin = trendTable_jourRepository.findFirstByChronoBetweenAndName(fileTimeMatin, fileTimeMatin + 6_000_000_000L, name);
                System.out.println(" ");
            }

            Date dateNow = new Date(System.currentTimeMillis() - 5 * 60);
            if (dateNow.compareTo(new SimpleDateFormat("dd.MM.yyyy" + "hh:mm").parse(day + "19:30")) <= 0) {
                Instant instant1 = dateNow.toInstant();
                System.out.println(instant1);
                long duree2 = fromInstant(instant1);
                if (trendTable_jourRepository.findFirstByChronoBetweenAndName(duree2, duree2 + 6_000_000_000L, name) != null) {
                    trendTable_jour_soir = trendTable_jourRepository.findFirstByChronoBetweenAndName(duree2, duree2 + 6_000_000_000L, name);
                    System.out.println("Consomation a l'heure systeme  de " + name + " = " + trendTable_jour_soir.getValue());

                }

            } else {
                Date date = new SimpleDateFormat("dd.MM.yyyy" + "hh:mm").parse(day + "19:25");
                Instant instant1 = date.toInstant();
                long duree2 = fromInstant(instant1);
                System.out.println(instant1);
                System.out.println("vox 2");
                if (trendTable_jourRepository.findFirstByChronoBetweenAndName(duree2, duree2 + 7_000_000_000L, name) != null) {
                    trendTable_jour_soir = trendTable_jourRepository.findFirstByChronoBetweenAndName(duree2, duree2 + 7_000_000_000L, name);
                    System.out.println("Consomation au soir de " + name + " = " + trendTable_jour_soir.getValue());

                } else {
                    System.out.println("Erreur pour recuperer la valeur actuelle de la production au soir de" + name);
                }
            }
            System.out.println("Valeur trendTable jour au  soir " + trendTable_jour_soir.getValue());
            trendTable_api.setProduction(trendTable_jour_soir.getValue() - trendTable_jour_matin.getValue() + trendTable_api.getProduction());
        }

        System.out.println(toInstant(fileTimeMatin + 6_000_000_000L));
        trendTable_api.setTimestamp(new Timestamp(date1.getTime()));


        return trendTable_api;


    }

    /**
     * https://stackoverflow.com/questions/5398557/java-library-for-dealing-with-win32-filetime
     */
    public static final Instant ZERO = Instant.parse("1601-01-01T00:00:00Z");

    /**
     * https://stackoverflow.com/questions/5398557/java-library-for-dealing-with-win32-filetime
     *
     * @param instant
     * @return long timefile timestamp
     */
    public static long fromInstant(Instant instant) {
        Duration duration = Duration.between(ZERO, instant);
        return duration.getSeconds() * 10_000_000 + duration.getNano() / 100;
    }

    /**
     * https://stackoverflow.com/questions/5398557/java-library-for-dealing-with-win32-filetime
     *
     * @param fileTime timestamp
     * @return Instant
     */
    public static Instant toInstant(long fileTime) {
        Duration duration = Duration.of(fileTime / 10, ChronoUnit.MICROS).plus(fileTime % 10 * 100, ChronoUnit.NANOS);
        return ZERO.plus(duration);
    }


    public static ArrayList<Date> getDaysBetweenDates(Date startdate, Date enddate) {
        ArrayList<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

}

