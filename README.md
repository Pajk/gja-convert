# CONVERT

_Program pro převod hudebních formátů_

## Představení

Program Convert umí převést širokou škálu různých hudebních formátů do formátu OGG nebo FLAC. Uživatel si může nastavit potřebné parametry výstupního formátu, má tedy kontrolu nad kvalitou převedených skladeb. Convert má příjemně jednoduché uživatelské rozhraní, skladby je možné přetáhnout do okna programu na ikonu formátu, do kterého mají být převedeny. Program už pak podle nastavení vygeneruje nový název souboru, převede jej a uloží do předem nastaveného adresáře. 
Convert je napsaný v programovací jazyce Java a pro převod hudebních formátů využívá upravené knihovny [JAVE](http://www.sauronsoftware.it/projects/jave/). Jedná se o multiplatformní program, můžou ho tedy používat uživatelé MS Windows i GNU/Linux (na jiných systémech nebyl testován). Program je přeložen do dvou jazyků, angličtiny a češtiny, a je přichystán na přidání dalších překladů. Na počítačích s vícejádrovými procesory nebo dokonce s více procesory by byla škoda nevyužít všechen dostupný výkon, proto program Convert využívá ke své práci vlákna a umí převádět více souborů najednou. Kolik jich má proram převádět v jeden čas si uživatel může nastavit a přizpůsobit tak Convert k výkonu svého PC

## Použití

Hlavní okno programu je navrženo pro co nejjednodušší použití. Je rozděleno na dvě části, v horní jsou zobrazeny ikony formátů OGG a FLAC, na které je možno přetáhnout soubor nebo více souborů skladeb, které chce uživatel převést. Pokud jsou přetaženy na symbol OGG, převedou se do formátu OGG, pokud na symbol FLAC, převedou se do formátu FLAC.
Ve spodní části okna se pak zobrazují jednotlivé soubory a stav jejich převodu, popřípadě chybová hláška, pokud se něco při převodu nepovedlo.
Soubory je možné vybrat i standardně pomocí dialogu výběru souboru, k tomu je možné se dostat přes menu Soubor -> Konvertovat do OGG nebo FLAC. Další možnost, jak otevřít dialog, je poklepat na ikonu formátu, do kterého chce uživatel skladby převádět.
Po úspěšném převodu je možné otevřít skladbu v defaultně nastaveném hudebním přehrávači dvojitým poklepáním na ukazatel průběhu převodu.
Pro pohodlnější ovládání programu jsou podporovány klávesové zkratky.

## Nastavení

K dialogu nastavení je možné se dostat přes menu Nápověda -> Nastavení nebo klávesou zkratku alt+s. Dialog je rozdělen na tři sekce. V případě nejastností je k dispozici nápověda, která se zobrazí po najetí na název nastavované hodnoty.

## Obecné

 * Maximální počet převáděných souborů - počet vláken, které program využije
 * Jazyk aplikace - zatím je možno vybrat mezi angličtinou a češtinou
 * Název výstupního souboru - zde je možnost si poskládat vzor, podle kterého se budou generovat názvy převedených souborů
 * Formát názvu  - nastavení pro soubory .cue, podle kterého se zjistí informace o skladbách
 * Složka pro uložení zkonvertovaných souborů - defaultně program vytvoří a používá složku s názvem ConvertedMusic v domovském adresáři uživatele

### Formát OGG

Zde je možné nastavit parametry pro převod souboru do OGG.

 * [Kvalita](http://en.wikipedia.org/wiki/Vorbis#Technical_details) - nejnižší je 0 a horní hranice v podstatě není. Doporučená kvalita je 40-50, což zhruba odpovídá 128-160kbps.
 * [Sampling rate](http://en.wikipedia.org/wiki/Sampling_rate)
 * Hlasitost


### Formát FLAC

Nastavení pro převod skladeb do formátu FLAC, obdobně jako u OGG.
 
 * Sampling rate
 * Počet kanálů - mono (1), stereo (2), 2.1 (3), 4.0 (4), 4.1 (5), 5.1 (6)
 * Hlasitost
