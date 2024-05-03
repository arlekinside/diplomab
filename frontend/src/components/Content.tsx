import Params from "../Params";

function Content(props: { children: any }) {
    return (
        <div style={{minWidth: "500px", width: '70%', marginTop: "2vh", marginBottom: "2vh",
            backgroundColor: Params.colors.background, borderRadius: "10px"}}
        >
            {props.children}
        </div>
    );
}

export default Content;