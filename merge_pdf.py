from PyPDF2 import PdfMerger

def merge_pdfs(pdf1_path, pdf2_path, output_path):
    merger = PdfMerger()

    merger.append(pdf1_path)
    merger.append(pdf2_path)

    with open(output_path, "wb") as f:
        merger.write(f)

    merger.close()

# Example usage
merge_pdfs("docs/DESIGN_PATTERNS.pdf", "code_metrics/CODE_QUALITY_METRICS_ANALYSIS.pdf", "Assignment2_29.pdf")