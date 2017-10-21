# Output
set datafile separator ',' # csv separator
set terminal png enhanced font "arial,11" fontscale 1.3
set output 'img/s_mis_and_steiner_score.png'


# Legend
set key left top


# Labels
set xlabel 'Nombre de points'
set ylabel 'Taille du MCDS'

# Plot
plot 'benchmark.csv' u 1:2 w linespoint title 'S-MIS', 'benchmark.csv' u 1:3 w linespoint title 'Steiner'