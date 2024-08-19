/*
    Die Punkte im Spiel werden bei jeder Aktion mitgezählt.
    So wird es auch ermöglicht, dass das Spiel von der Person begonnen wird, die nach turnCards() die höchste Summe auf der Hand hat.
    Am Ende des Spiels wird die Spielfunktion nicht abgebrochen, sondern mit folgenden Optionen versehen:
            Spiel beenden
            Weiterspielen (Dann wird das Spielergebnis in die nächste Runde mitgenommen)
            Spiel wird automatisch beendet, weil die maximale Punktzahl eines Spielers erreicht wird - Neues Spiel wird angeboten, das wieder bei 0 startet

    Karte ziehen
            Auswahl, ob vom Ablegestapel oder dem Nachziehstapel
                bei Nachziehstapel wird Karte angezeigt
                Spieler bekommt Option, ob er die Karte behalten möchte
                    JA: er muss mitKoordinate aussuchen, mit welcher vorhandenen Karte er tauschen möchte (darf auch verdeckt sein)
                    NEIN: Die Karte wird auf den Ablegestapel abgeworfen und eine der verdeckten Karten muss aufgedeckt werden
            sollte durch das Ziehen und Annehmen der Karte im eigenen Stapel ein senkrechtes Triple entstehen, wird dieses aus der hand entfernt und auf den Ablegestapel abgeworfen
    sobald ein Spieler die letzte verdeckte Karte aufgedeckt hat, darf jeder andere Spieler noch einen Zug machen.
    Danach wird die Runde abgerechnet.
 */