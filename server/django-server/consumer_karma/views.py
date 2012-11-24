from django.shortcuts import render, render_to_response
from django.http import HttpResponse, HttpResponseRedirect

import ParsePy

from consumer_karma.forms import CompanyForm

# Initialize Parse
ParsePy.APPLICATION_ID = "xXnJr64tDsIdqugTpfJxg9nKaUE6oEH6tg3M7j74"
ParsePy.REST_API_KEY = "dAc70AZmfJNuvIDExvBaZ5QuI6XOEk3IkpUxYsja"

# API
def index(request):
    return render_to_response('index.html')

def companies(request):
    companies = ParsePy.ParseQuery("Company").fetch()
    return render_to_response('companies.html', {'companies': companies})

def items(request):
    return HttpResponse('Items!')

def new_item(request):
    return HttpResponse('New Item!')

def new_company(request):
    if request.method == 'POST': # if form has been submitted
        form = CompanyForm(request.POST)
        if form.is_valid():
            newCompany = ParsePy.ParseObject("Company")
            newCompany.name = form.cleaned_data['name']
            newCompany.description = form.cleaned_data['description']
            newCompany.save()
            return HttpResponseRedirect('/thanks/')
    else:
        parents = ParsePy.ParseQuery("Company").fetch()
        form = CompanyForm() # if form not submitted
    return render(request, 'new_company.html', {
        'form': form,
    })

def produce(request):
    # create random item
    newItem = ParsePy.ParseObject("Item")
    newItem.title="Test Item"
    newItem.description="Testing parse with Django"
    newItem.unethical_animal_practices=False
    newItem.corporate_irresponsibility=False
    newItem.environmentally_unsustainable=False
    newItem.exploited_labor=False
    newItem.save()

def thanks(request):
    return HttpResponse('Thanks!')
