# Output
set datafile separator ',' # csv separator
set terminal png enhanced font "arial,11" fontscale 1.3
set output 'img/s_mis_and_steiner_time.png'


# Legend
set key left top


# Labels
set xlabel 'Nombre de points'
set ylabel 'Temps de calcul (s)'

# Plot
plot 'benchmark.csv' u 1:5 w linespoint title 'S-MIS', 'benchmark.csv' u 1:6 w linespoint title 'Steiner'