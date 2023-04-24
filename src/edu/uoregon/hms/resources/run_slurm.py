#!/usr/bin/env python

# from simple_slurm import Slurm
from pathlib import Path
import subprocess

# cwd = os.getcwd()

job_ids = []
    
def slurm_submitter():
	for path in Path().rglob("q-tala-gauss"):
		print(path)
		job_id = slurm.sbatch('path')
		job_ids.append(job_id)


def slurm_run():
	for qpath in Path().rglob("q-tala-gauss"):
		print(qpath)
		submit = subprocess.run(["sbatch", qpath], capture_output=True,text=True)
		_, _, _, job_id = submit.stdout.split()
		job_ids.append(job_id)


def slurm_trigger():
	subprocess.run(["strigger", "--set", "-j", job_id, "-f", "-p", "date"])


def id_file(id_list):
	with open("submitted_jobIDs.txt",'w') as f:
		for id in id_list:
			print(id, file=f)

def main():
	# slurm_submitter()
	slurm_run()
	id_file(job_ids)


if __name__=="__main__":
	main()

