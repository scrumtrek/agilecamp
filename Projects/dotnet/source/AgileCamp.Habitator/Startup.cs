using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(AgileCamp.Habitator.Startup))]
namespace AgileCamp.Habitator
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
