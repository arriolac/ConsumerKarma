
# Form Classes
from django import forms

import ParsePy

class CompanyForm(forms.Form):
#    def __init__(self, parents):
       # super(CompanyForm, self).__init__()
       # print "hello"
       # self._parents = parents

    name = forms.CharField(max_length=100)
    description = forms.CharField()
    #parent = forms.ChoiceField(choices=_parents)

    # Get all companies
    #parents = ParsePy.ParseQuery("Company").fetch()
    #parent = forms.ChoiceField(choices=companies)
