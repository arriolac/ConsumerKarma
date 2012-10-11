from django.conf.urls import patterns, include, url

# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'consumer_karma.views.home', name='home'),
    # url(r'^consumer_karma/', include('consumer_karma.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    url(r'^$', 'consumer_karma.views.index'),
    url(r'^companies/$', 'consumer_karma.views.companies'),
    url(r'^companies/new_company$', 'consumer_karma.views.new_company'),
    url(r'^items/$', 'consumer_karma.views.items'),
    url(r'^items/new_item$', 'consumer_karma.views.new_item'),
    url(r'^produce/$', 'consumer_karma.views.produce'),
    url(r'^thanks/$', 'consumer_karma.views.thanks'),
    # url(r'^admin/', include(admin.site.urls)),
)
