
# Form Classes
from django import forms

class CompanyForm(forms.Form):
    title = forms.CharField(max_length=100)
    description = forms.CharField()
