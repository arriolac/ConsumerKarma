from django.shortcuts import render
from django.http import HttpResponse, HttpResponseRedirect

import ParsePy

from consumer_karma.forms import CompanyForm

# Initialize Parse
ParsePy.APPLICATION_ID = "xXnJr64tDsIdqugTpfJxg9nKaUE6oEH6tg3M7j74"
ParsePy.REST_API_KEY = "dAc70AZmfJNuvIDExvBaZ5QuI6XOEk3IkpUxYsja"

# API
def index(request):
    return HttpResponse('ConsumerKarma Hello World!')

def companies(request):
    return HttpResponse('Companies!')

def items(request):
    return HttpResponse('Items!')

def new_item(request):
    return HttpResponse('New Item!')

def new_company(request):
    if request.method == 'POST': # if form has been submitted
        form = CompanyForm(request.POST)
        if form.is_valid():
            return HttpResponseRedirect('/thanks/')
    else:
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
