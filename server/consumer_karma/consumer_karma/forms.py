
# Form Classes
from django import forms

class CompanyForm(forms.Form):
    name = forms.CharField(max_length=100)
    description = forms.CharField()

    def is_valid(self):
        # TODO: write form validation check
        return True
