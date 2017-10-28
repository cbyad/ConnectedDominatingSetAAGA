.PHONY: clean all

all: s_mis_score  s_mis_and_steiner_score  s_mis_and_steiner_time


s_mis_score: gnuplot/s_mis.plt benchmark.csv
	gnuplot gnuplot/s_mis.plt

s_mis_and_steiner_time: gnuplot/s_mis_and_steiner_time.plt benchmark.csv
	gnuplot gnuplot/s_mis_and_steiner_time.plt

s_mis_and_steiner_score: gnuplot/s_mis_and_steiner_score.plt benchmark.csv
	gnuplot gnuplot/s_mis_and_steiner_score.plt

clean:
	rm -rf *.log *.out *.aux *.png *.csv
