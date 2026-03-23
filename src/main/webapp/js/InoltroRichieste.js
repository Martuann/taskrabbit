function calcolaPrezzoTotale() {
        let profSelect = document.querySelector('select[name="id_professione"]');
        let prezzoOra = 0;
        if (profSelect.selectedIndex >= 0) {
            prezzoOra = parseFloat(profSelect.options[profSelect.selectedIndex].getAttribute('data-prezzo')) || 0;
        }

        let veicoloSelect = document.querySelector('select[name="id_veicolo"]');
        let prezzoVeicolo = 0;
        if (veicoloSelect.selectedIndex >= 0) {
            prezzoVeicolo = parseFloat(veicoloSelect.options[veicoloSelect.selectedIndex].getAttribute('data-prezzo')) || 0;
        }

        let oreSelezionate = document.querySelector('input[name="durata_ore"]:checked');
        let ore = oreSelezionate ? parseFloat(oreSelezionate.value) : 1;

        let totale = (prezzoOra * ore) + prezzoVeicolo;

        document.getElementById('prezzo_totale').innerText = totale.toFixed(2);
    }

    document.addEventListener("DOMContentLoaded", function() {
        document.querySelector('select[name="id_professione"]').addEventListener('change', calcolaPrezzoTotale);
        document.querySelector('select[name="id_veicolo"]').addEventListener('change', calcolaPrezzoTotale);
        
        let radioOre = document.querySelectorAll('input[name="durata_ore"]');
        radioOre.forEach(r => r.addEventListener('change', calcolaPrezzoTotale));
    });

























(function() {
    const disponibilitas = [];
    const mappeGiorni = {};

    const datiDinamici = window.blocchiGrezzi || [];

    datiDinamici.forEach(b => {
        if (!mappeGiorni[b.data]) {
            mappeGiorni[b.data] = { data: b.data, inizio: b.inizio, fine: b.fine };
            disponibilitas.push(mappeGiorni[b.data]);
        } else {
            if (b.inizio === mappeGiorni[b.data].fine) {
                mappeGiorni[b.data].fine = b.fine;
            }
        }
    });

    const toMin = (t) => { 
        if(!t) return 0;
        const p = t.split(':'); 
        return parseInt(p[0]) * 60 + parseInt(p[1]); 
    };
    
    const fromMin = (m) => {
        const h = Math.floor(m / 60);
        const min = m % 60;
        return h.toString().padStart(2, '0') + ":" + min.toString().padStart(2, '0');
    };

    window.popolaOrari = function(dataStr) {
        const select = document.getElementById('ora_inizio_select');
        const durataInput = document.querySelector('input[name="durata_ore"]:checked');
        const durataOre = durataInput ? parseInt(durataInput.value) : 1;
        
        if(!select) return;
        select.innerHTML = '<option value="">Seleziona orario</option>';

        const info = disponibilitas.find(d => d.data === dataStr);
        if (!info) return;

        const startMin = toMin(info.inizio);
        const endMin = toMin(info.fine);
        const limiteInizio = endMin - (durataOre * 60);

        let slotTrovati = 0;
        for (let m = startMin; m <= limiteInizio; m += 30) {
            const opt = document.createElement('option');
            const oraLabel = fromMin(m);
            opt.value = oraLabel;
            opt.textContent = oraLabel;
            select.appendChild(opt);
            slotTrovati++;
        }

        if(slotTrovati === 0) {
            select.innerHTML = '<option value="">Nessun orario per questa durata</option>';
        }
    };

    window.renderCalendar = function() {
        const cal = document.getElementById('visual-calendar');
        if(!cal) return;
        cal.innerHTML = '';
        
        ['Dom', 'Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab'].forEach(l => {
            const h = document.createElement('div');
            h.className = 'cal-header';
            h.textContent = l;
            cal.appendChild(h);
        });

        const oggi = new Date();
        oggi.setHours(0,0,0,0);
        
        const startOffset = oggi.getDay(); 
        for (let i = 0; i < startOffset; i++) {
            cal.appendChild(document.createElement('div'));
        }

        for (let i = 0; i < 14; i++) {
            const dataCorrente = new Date(oggi);
            dataCorrente.setDate(oggi.getDate() + i);

            const y = dataCorrente.getFullYear();
            const m = String(dataCorrente.getMonth() + 1).padStart(2, '0');
            const d = String(dataCorrente.getDate()).padStart(2, '0');
            const dataISO = y + "-" + m + "-" + d;
            
            const disp = disponibilitas.find(x => x.data === dataISO);
            const dayDiv = document.createElement('div');
            dayDiv.className = 'cal-day';
            dayDiv.textContent = dataCorrente.getDate();

            if (disp) {
                dayDiv.classList.add('available');
                dayDiv.onclick = function() {
                    document.querySelectorAll('.cal-day').forEach(el => el.classList.remove('selected'));
                    this.classList.add('selected');
                    const hiddenInput = document.getElementById('data_scelta_hidden');
                    if(hiddenInput) hiddenInput.value = dataISO;
                    window.popolaOrari(dataISO);
                };
            } else {
                dayDiv.classList.add('disabled');
                dayDiv.style.opacity = "0.3";
            }
            cal.appendChild(dayDiv);
        }

        const finePeriodo = new Date(oggi);
        finePeriodo.setDate(oggi.getDate() + 13);
        const meseInizio = oggi.toLocaleDateString('it-IT', { month: 'long' });
        const meseFine = finePeriodo.toLocaleDateString('it-IT', { month: 'long' });
        const titolo = document.getElementById('calendar-header-title');
        if(titolo) {
            titolo.textContent = (meseInizio === meseFine) ? 
                meseInizio + " " + oggi.getFullYear() : 
                meseInizio + " — " + meseFine + " " + finePeriodo.getFullYear();
        }
    };

    if (document.readyState === "complete" || document.readyState === "interactive") {
        renderCalendar();
    } else {
        document.addEventListener("DOMContentLoaded", renderCalendar);
    }
})();

function aggiornaDati() {
    const hiddenInput = document.getElementById('data_scelta_hidden');
    const dataH = hiddenInput ? hiddenInput.value : null;
    if (dataH) window.popolaOrari(dataH);
}