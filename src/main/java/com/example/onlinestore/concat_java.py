import os

output_file = "all_java_code.txt"

with open(output_file, "w", encoding="utf-8") as outfile:
    for root, dirs, files in os.walk("."):
        for file in files:
            if file.endswith(".java"):
                file_path = os.path.join(root, file)
                outfile.write(f"----- {file_path} -----\n")
                with open(file_path, "r", encoding="utf-8") as infile:
                    outfile.write(infile.read())
                    outfile.write("\n\n")
